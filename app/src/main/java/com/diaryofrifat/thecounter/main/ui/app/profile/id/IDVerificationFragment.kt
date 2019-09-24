package com.diaryofrifat.thecounter.main.ui.app.profile.id

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.diaryofrifat.thecounter.R
import com.diaryofrifat.thecounter.main.data.localandremote.model.user.UserEntity
import com.diaryofrifat.thecounter.main.ui.base.component.BaseFragment
import com.diaryofrifat.thecounter.main.ui.base.helper.ProgressDialogUtils
import com.diaryofrifat.thecounter.main.ui.base.loadImage
import com.diaryofrifat.thecounter.main.ui.base.makeItGone
import com.diaryofrifat.thecounter.main.ui.base.setRipple
import com.diaryofrifat.thecounter.utils.helper.Constants
import com.diaryofrifat.thecounter.utils.helper.PermissionUtils
import com.diaryofrifat.thecounter.utils.helper.ViewUtils
import com.diaryofrifat.thecounter.utils.helper.imagepicker.ImagePickerUtils
import com.diaryofrifat.thecounter.utils.libs.ImageCropperUtils
import com.diaryofrifat.thecounter.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.custom_alert_dialog_for_id_verification.view.*
import kotlinx.android.synthetic.main.fragment_id_verification.*
import timber.log.Timber
import java.io.File

class IDVerificationFragment : BaseFragment<IDVerificationMvpView, IDVerificationPresenter>(),
    IDVerificationMvpView {

    companion object {
        const val TYPE_PASSPORT = 1
        const val TYPE_NID = 2
        const val TYPE_UTILITY = 3
    }

    private var mClickedView: View? = null
    private var mDialogView: View? = null
    private var mIdType: Int? = null

    override val layoutId: Int
        get() = R.layout.fragment_id_verification

    override fun getFragmentPresenter(): IDVerificationPresenter {
        return IDVerificationPresenter()
    }

    override fun startUI() {
        initialize()
        setListeners()
        loadData()
    }

    private fun loadData() {
        presenter.getUserFromDatabase()
    }

    private fun setListeners() {
        setClickListener(text_view_passport, text_view_nid, text_view_utility_bill)
    }

    private fun initialize() {
        text_view_nid.setRipple(R.color.colorWhite50)
        text_view_passport.setRipple(R.color.colorWhite50)
        text_view_utility_bill.setRipple(R.color.colorWhite50)
    }

    override fun stopUI() {

    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.text_view_nid -> {
                showAlertDialog(TYPE_NID)
            }

            R.id.text_view_passport -> {
                showAlertDialog(TYPE_PASSPORT)
            }

            R.id.text_view_utility_bill -> {
                showAlertDialog(TYPE_UTILITY)
            }
        }
    }

    fun showAlertDialog(idType: Int) {
        val builder = AlertDialog.Builder(mContext!!)
        builder.setCancelable(false)

        val dialogView = LayoutInflater.from(mContext!!)
            .inflate(R.layout.custom_alert_dialog_for_id_verification, null, false)
        builder.setView(dialogView)

        dialogView.text_view_upload.setRipple(R.color.colorWhite26)
        dialogView.text_view_cancel.setRipple(R.color.colorWhite26)

        when (idType) {
            TYPE_NID -> {
                dialogView.text_view_title.text = getString(R.string.profile_upload_nid)
            }

            TYPE_PASSPORT -> {
                dialogView.image_view_back_side.makeItGone()
                dialogView.text_view_back_side.makeItGone()
                dialogView.text_view_front_side.text = getString(R.string.profile_photo)
                dialogView.text_view_title.text = getString(R.string.profile_upload_passport)
            }

            else -> {
                dialogView.image_view_back_side.makeItGone()
                dialogView.text_view_back_side.makeItGone()
                dialogView.text_view_front_side.text = getString(R.string.profile_photo)
                dialogView.text_view_title.text = getString(R.string.profile_upload_utility_bill)
            }
        }

        dialogView.image_view_front_side.setOnClickListener {
            pickImageAndShowAlert(idType, it, dialogView)
        }

        dialogView.text_view_front_side.setOnClickListener {
            pickImageAndShowAlert(idType, it, dialogView)
        }

        dialogView.image_view_back_side.setOnClickListener {
            pickImageAndShowAlert(idType, it, dialogView)
        }

        dialogView.text_view_back_side.setOnClickListener {
            pickImageAndShowAlert(idType, it, dialogView)
        }

        val dialog = builder.create()

        dialogView.text_view_cancel.setOnClickListener {
            dialog?.dismiss()
        }

        dialog.setOnDismissListener {
            presenter.imageFileMap.clear()
        }

        dialogView.text_view_upload.setOnClickListener {

            val isValid: Boolean = when (idType) {
                TYPE_NID -> {
                    presenter.imageFileMap.isNotEmpty() &&
                            presenter.imageFileMap.size == 2
                }

                else -> {
                    presenter.imageFileMap.isNotEmpty() &&
                            presenter.imageFileMap.size == 1
                }
            }

            if (isValid) {
                presenter.uploadUserID(
                    mContext!!, idType,
                    presenter.imageFileMap
                )

                dialog?.dismiss()
            } else {
                onError(getString(R.string.profile_activity_error_invalid_input))
            }
        }

        dialog?.show()
    }

    private fun pickImageAndShowAlert(idType: Int, view: View, dialogView: View) {
        mIdType = idType
        mClickedView = view
        mDialogView = dialogView

        if (PermissionUtils.requestPermission(
                this,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            ImagePickerUtils.pickImageAndCrop(
                this,
                object : ImageCropperUtils.Listener {
                    override fun onSuccess(
                        imageUri: Uri,
                        imageFile: File
                    ) {
                        when (view.id) {
                            R.id.text_view_front_side, R.id.image_view_front_side -> {
                                dialogView.image_view_front_side.loadImage(imageUri)

                                if (idType == TYPE_NID) {
                                    presenter.imageFileMap[IDVerificationPresenter.TYPE_FRONT] =
                                        imageFile
                                }
                            }

                            else -> {
                                dialogView.image_view_back_side.loadImage(imageUri)

                                if (idType == TYPE_NID) {
                                    presenter.imageFileMap[IDVerificationPresenter.TYPE_BACK] =
                                        imageFile
                                }
                            }
                        }

                        when (idType) {
                            TYPE_NID -> {
                                // Do nothing
                            }

                            else -> {
                                presenter.imageFileMap[IDVerificationPresenter.TYPE_FRONT] =
                                    imageFile
                            }
                        }
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
                if (mDialogView != null
                    && mIdType != null
                    && mClickedView != null
                ) {
                    pickImageAndShowAlert(
                        mIdType!!,
                        mClickedView!!, mDialogView!!
                    )
                }
            } else {
                ToastUtils.warning(getString(R.string.warning_permissions_are_required))
            }
        }
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onSuccess(message: String, type: Int) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(message)
    }

    override fun onGettingUser(user: UserEntity) {
        when (user.passportVerificationStatus) {
            Constants.Common.STATUS_USER_PHOTO_ID_APPROVED -> {
                text_view_passport.setCompoundDrawablesWithIntrinsicBounds(
                    ViewUtils.getDrawable(R.drawable.ic_green_tick),
                    null,
                    ViewUtils.getDrawable(R.drawable.ic_passport),
                    null
                )
            }

            Constants.Common.STATUS_USER_PHOTO_ID_SUBMITTED -> {
                text_view_passport.setCompoundDrawablesWithIntrinsicBounds(
                    ViewUtils.getDrawable(R.drawable.ic_yellow_tick),
                    null,
                    ViewUtils.getDrawable(R.drawable.ic_passport),
                    null
                )
            }

            else -> {
                text_view_passport.setCompoundDrawablesWithIntrinsicBounds(
                    ViewUtils.getDrawable(R.drawable.ic_red_tick),
                    null,
                    ViewUtils.getDrawable(R.drawable.ic_passport),
                    null
                )
            }
        }

        when (user.utilityBillVerificationStatus) {
            Constants.Common.STATUS_USER_PHOTO_ID_APPROVED -> {
                text_view_utility_bill.setCompoundDrawablesWithIntrinsicBounds(
                    ViewUtils.getDrawable(R.drawable.ic_green_tick),
                    null,
                    ViewUtils.getDrawable(R.drawable.ic_utility_bill),
                    null
                )
            }

            Constants.Common.STATUS_USER_PHOTO_ID_SUBMITTED -> {
                text_view_utility_bill.setCompoundDrawablesWithIntrinsicBounds(
                    ViewUtils.getDrawable(R.drawable.ic_yellow_tick),
                    null,
                    ViewUtils.getDrawable(R.drawable.ic_utility_bill),
                    null
                )
            }

            else -> {
                text_view_utility_bill.setCompoundDrawablesWithIntrinsicBounds(
                    ViewUtils.getDrawable(R.drawable.ic_red_tick),
                    null,
                    ViewUtils.getDrawable(R.drawable.ic_utility_bill),
                    null
                )
            }
        }

        when (user.nidVerificationStatus) {
            Constants.Common.STATUS_USER_PHOTO_ID_APPROVED -> {
                text_view_nid.setCompoundDrawablesWithIntrinsicBounds(
                    ViewUtils.getDrawable(R.drawable.ic_green_tick),
                    null,
                    ViewUtils.getDrawable(R.drawable.ic_nid),
                    null
                )
            }

            Constants.Common.STATUS_USER_PHOTO_ID_SUBMITTED -> {
                text_view_nid.setCompoundDrawablesWithIntrinsicBounds(
                    ViewUtils.getDrawable(R.drawable.ic_yellow_tick),
                    null,
                    ViewUtils.getDrawable(R.drawable.ic_nid),
                    null
                )
            }

            else -> {
                text_view_nid.setCompoundDrawablesWithIntrinsicBounds(
                    ViewUtils.getDrawable(R.drawable.ic_red_tick),
                    null,
                    ViewUtils.getDrawable(R.drawable.ic_nid),
                    null
                )
            }
        }
    }
}