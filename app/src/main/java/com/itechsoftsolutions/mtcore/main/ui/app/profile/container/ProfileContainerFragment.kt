package com.itechsoftsolutions.mtcore.main.ui.app.profile.container

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.itechsoftsolutions.mtcore.R
import com.itechsoftsolutions.mtcore.main.data.localandremote.model.user.UserEntity
import com.itechsoftsolutions.mtcore.main.data.remote.model.ProfileBadge
import com.itechsoftsolutions.mtcore.main.ui.app.landing.container.ContainerActivity
import com.itechsoftsolutions.mtcore.main.ui.app.others.fullscreenphoto.PhotoFullScreenActivity
import com.itechsoftsolutions.mtcore.main.ui.base.callback.ItemClickListener
import com.itechsoftsolutions.mtcore.main.ui.base.component.BaseFragment
import com.itechsoftsolutions.mtcore.main.ui.base.helper.AlertDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.helper.ProgressDialogUtils
import com.itechsoftsolutions.mtcore.main.ui.base.initializeRecyclerView
import com.itechsoftsolutions.mtcore.main.ui.base.toTitleCase
import com.itechsoftsolutions.mtcore.utils.helper.PermissionUtils
import com.itechsoftsolutions.mtcore.utils.helper.imagepicker.ImagePickerUtils
import com.itechsoftsolutions.mtcore.utils.libs.GlideUtils
import com.itechsoftsolutions.mtcore.utils.libs.ImageCropperUtils
import com.itechsoftsolutions.mtcore.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_profile_container.*
import timber.log.Timber
import java.io.File
import java.util.*

class ProfileContainerFragment :
    BaseFragment<ProfileContainerMvpView, ProfileContainerPresenter>(),
    ProfileContainerMvpView {

    private var mAdapter: ProfileTabViewPagerAdapter? = null
    private var mSelectedPhotoSource: Int? = null
    private var mProfilePictureUrl: String? = null

    override val layoutId: Int
        get() = R.layout.fragment_profile_container

    override fun getFragmentPresenter(): ProfileContainerPresenter {
        return ProfileContainerPresenter()
    }

    override fun startUI() {
        initialize()
        setListeners()
        loadData()
    }

    private fun loadData() {
        presenter.getUserFromCloud(mContext!!)
        presenter.getUserFromDatabase()
        presenter.getProfileBadges(mContext!!)
    }

    private fun setListeners() {
        setClickListener(
            image_view_camera, image_view_gallery,
            image_view_profile_picture
        )
    }

    private fun initialize() {
        (activity as ContainerActivity).setPageTitle(getString(R.string.profile_title).toTitleCase())

        initializeRecyclerView(
            recycler_view_badges,
            ProfileBadgeAdapter(),
            object : ItemClickListener<ProfileBadge> {
                override fun onItemClick(view: View, item: ProfileBadge) {
                    super.onItemClick(view, item)
                    ToastUtils.nativeLong(item.title)
                }
            },
            null,
            LinearLayoutManager(mContext!!, RecyclerView.HORIZONTAL, false),
            null,
            null,
            null,
            null
        )

        setupViewPager()
    }

    override fun stopUI() {

    }

    private fun getAdapter(): ProfileBadgeAdapter {
        return recycler_view_badges.adapter as ProfileBadgeAdapter
    }

    private fun setupViewPager() {
        tab_layout.setupWithViewPager(view_pager)
        mAdapter =
            ProfileTabViewPagerAdapter(
                childFragmentManager,
                mContext!!
            )
        view_pager.adapter = mAdapter
    }

    override fun onError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }

    override fun onGettingProfileBadgeList(list: List<ProfileBadge>) {
        getAdapter().clear()
        getAdapter().addItems(list)
        ProgressDialogUtils.on().hideProgressDialog()
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.image_view_camera -> {
                mSelectedPhotoSource = ImagePickerUtils.ONLY_CAMERA
                if (PermissionUtils.requestPermission(
                        this,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    if (mSelectedPhotoSource != null) {
                        pickImageAndShowAlert(mSelectedPhotoSource!!)
                    }
                }
            }

            R.id.image_view_gallery -> {
                mSelectedPhotoSource = ImagePickerUtils.ONLY_GALLERY
                if (PermissionUtils.requestPermission(
                        this,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    if (mSelectedPhotoSource != null) {
                        pickImageAndShowAlert(mSelectedPhotoSource!!)
                    }
                }
            }

            R.id.image_view_profile_picture -> {
                if (!TextUtils.isEmpty(mProfilePictureUrl)) {
                    PhotoFullScreenActivity.startActivity(mContext!!, mProfilePictureUrl!!)
                }
            }
        }
    }

    private fun pickImageAndShowAlert(contentCode: Int) {
        ImagePickerUtils.pickImageAndCrop(
            this,
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

                            // Upload profile picture
                            presenter.uploadProfilePicture(mContext!!, imageFile)
                        },
                        getString(R.string.no),
                        DialogInterface.OnClickListener { dialog, _ ->
                            dialog.dismiss()
                        },
                        getString(R.string.are_you_sure),
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
            }, contentCode
        )
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
                if (mSelectedPhotoSource != null) {
                    pickImageAndShowAlert(mSelectedPhotoSource!!)
                }
            } else {
                ToastUtils.warning(getString(R.string.warning_permissions_are_required))
            }
        }
    }

    override fun onUploadingProfilePicture(profilePictureUrl: String) {
        GlideUtils.custom(
            image_view_profile_picture,
            profilePictureUrl,
            RequestOptions()
                .placeholder(R.drawable.ic_user_avatar)
                .error(R.drawable.ic_user_avatar)
                .circleCrop()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC),
            null
        )

        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(getString(R.string.profile_activity_uploaded_successfully))
        presenter.getUserFromCloud(mContext!!)
    }

    override fun onGettingUser(user: UserEntity) {
        ProgressDialogUtils.on().hideProgressDialog()

        if (!TextUtils.isEmpty(user.photo)) {
            mProfilePictureUrl = user.photo
        }

        GlideUtils.custom(
            image_view_profile_picture,
            user.photo,
            RequestOptions()
                .placeholder(R.drawable.ic_user_avatar)
                .error(R.drawable.ic_user_avatar)
                .circleCrop()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC),
            null
        )

        text_view_name.text =
            String.format(
                Locale.getDefault(),
                getString(R.string.profile_full_name),
                user.firstName, user.lastName
            )
    }
}