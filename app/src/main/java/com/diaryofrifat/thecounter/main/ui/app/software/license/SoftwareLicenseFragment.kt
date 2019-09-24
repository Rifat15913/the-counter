package com.diaryofrifat.thecounter.main.ui.app.software.license

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.localandremote.model.software.SoftwareStatusEntity
import com.diaryofrifat.thecounter.main.ui.app.software.buysoftware.BuySoftwareActivity
import com.diaryofrifat.thecounter.main.ui.base.callback.ItemClickListener
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.main.ui.base.helper.AlertDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.initializeRecyclerView
import com.diaryofrifat.thecounter.main.ui.base.toTitleCase
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.PermissionUtils
import com.diaryofrifat.thecounter.utils.helper.imagepicker.ImagePickerUtils
import com.diaryofrifat.thecounter.utils.libs.ImageCropperUtils
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import com.nbsp.materialfilepicker.MaterialFilePicker
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import kotlinx.android.synthetic.main.fragment_software_license.*
import timber.log.Timber
import java.io.File
import java.util.regex.Pattern


class SoftwareLicenseFragment : BaseFragment<SoftwareLicenseMvpView, SoftwareLicensePresenter>(),
    SoftwareLicenseMvpView {

    companion object {
        const val REQUEST_CODE_PICK_PDF_FILE = 1
    }

    private var mClickedSoftwareItem: SoftwareStatusEntity? = null
    private var mPickedOption: Int? = null

    override val layoutId: Int
        get() = R.layout.fragment_software_license

    override fun getFragmentPresenter(): SoftwareLicensePresenter {
        return SoftwareLicensePresenter()
    }

    override fun startUI() {
        initializeRecyclerView(
            recycler_view_softwares,
            SoftwareLicenseAdapter(),
            object : ItemClickListener<SoftwareStatusEntity> {
                override fun onItemClick(view: View, item: SoftwareStatusEntity) {
                    super.onItemClick(view, item)

                    when (view.id) {
                        R.id.text_view_action -> {
                            when ((view as TextView).text) {
                                getString(R.string.software_license_buy_now).toTitleCase() -> {
                                    BuySoftwareActivity.startActivity(mContext!!, item)
                                }

                                getString(R.string.software_license_upload_bank_receipt).toTitleCase() -> {
                                    mClickedSoftwareItem = item

                                    val builder =
                                        AlertDialog.Builder(mContext!!, R.style.CustomAlertDialog)
                                    builder.setTitle(getString(R.string.software_license_choose_option))
                                    val options =
                                        resources.getStringArray(R.array.uploading_options)

                                    builder.setItems(options) { dialog, which ->
                                        mPickedOption = which

                                        when (which) {
                                            0 -> {
                                                if (PermissionUtils.requestPermission(
                                                        this@SoftwareLicenseFragment,
                                                        Manifest.permission.CAMERA,
                                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                                    )
                                                ) {
                                                    pickImageAndShowAlert()
                                                }
                                            }

                                            else -> {
                                                if (PermissionUtils.requestPermission(
                                                        this@SoftwareLicenseFragment,
                                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                                    )
                                                ) {
                                                    pickPdfFile()
                                                }
                                            }
                                        }

                                        dialog?.dismiss()
                                    }

                                    builder.create().show()
                                }

                                else -> {
                                    // Do nothing
                                }
                            }
                        }
                    }
                }
            },
            null,
            LinearLayoutManager(mContext!!),
            null,
            null,
            null,
            null
        )
    }

    private fun pickPdfFile() {
        MaterialFilePicker()
            .withSupportFragment(this@SoftwareLicenseFragment)
            .withRequestCode(REQUEST_CODE_PICK_PDF_FILE)
            .withFilter(Pattern.compile(Constants.Common.REGEX_PDF_FILE))
            .withHiddenFiles(true)
            .withTitle(getString(R.string.software_license_choose_a_pdf_file))
            .start()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun pickImageAndShowAlert() {
        ImagePickerUtils.pickImageAndCrop(
            this@SoftwareLicenseFragment,
            object : ImageCropperUtils.Listener {
                override fun onSuccess(
                    imageUri: Uri,
                    imageFile: File
                ) {
                    AlertDialogUtils.on().showNativeDialog(
                        mContext!!,
                        true,
                        getString(R.string.yes),
                        DialogInterface.OnClickListener { dialog, _ ->
                            dialog.dismiss()

                            if (mClickedSoftwareItem != null) {
                                presenter.uploadBankReceipt(
                                    mContext!!,
                                    mClickedSoftwareItem?.bankSubscriptionID!!,
                                    imageFile,
                                    false
                                )
                            }
                        },
                        getString(R.string.no),
                        DialogInterface.OnClickListener { dialog, _ ->
                            dialog.dismiss()
                        },
                        getString(R.string.software_license_are_you_sure),
                        null,
                        null
                    )
                }

                override fun onError(error: Throwable) {
                    Timber.d(error)

                    if (!TextUtils.isEmpty(error.message)) {
                        ToastUtils.error(error.message!!)
                    }
                }
            }, ImagePickerUtils.CAMERA_AND_GALLERY
        )
    }

    private fun loadData() {
        presenter.getSoftwareListFromCloud(mContext!!)
        presenter.getSoftwareStatusList(mContext!!)
    }

    override fun stopUI() {

    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onGettingSoftwareStatusList(list: List<SoftwareStatusEntity>) {
        getAdapter().clear()
        getAdapter().addItems(list)
    }

    private fun getAdapter(): SoftwareLicenseAdapter {
        return recycler_view_softwares.adapter as SoftwareLicenseAdapter
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var isGranted = true
        if (requestCode == PermissionUtils.REQUEST_CODE_PERMISSION_DEFAULT) {
            for (element in grantResults) {
                if (element != PackageManager.PERMISSION_GRANTED) {
                    isGranted = false
                }
            }

            if (isGranted) {
                if (mPickedOption != null) {
                    if (mPickedOption == 0) {
                        pickImageAndShowAlert()
                    } else {
                        pickPdfFile()
                    }
                }
            } else {
                ToastUtils.warning(getString(R.string.warning_permissions_are_required))
            }
        }
    }

    override fun onUploadingBankReceipt() {
        loadData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_PDF_FILE && resultCode == RESULT_OK) {
            val filePath = data?.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)
            val file = File(filePath)

            if (file.isFile) {
                AlertDialogUtils.on().showNativeDialog(
                    mContext!!,
                    true,
                    getString(R.string.yes),
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()

                        if (mClickedSoftwareItem != null) {
                            presenter.uploadBankReceipt(
                                mContext!!,
                                mClickedSoftwareItem?.bankSubscriptionID!!,
                                file,
                                true
                            )
                        }
                    },
                    getString(R.string.no),
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    },
                    getString(R.string.software_license_are_you_sure),
                    null,
                    null
                )
            }
        }
    }
}