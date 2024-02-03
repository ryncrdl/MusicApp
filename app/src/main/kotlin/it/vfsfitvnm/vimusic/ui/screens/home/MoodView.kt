package it.vfsfitvnm.vimusic.ui.screens.home

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import it.vfsfitvnm.vimusic.Action
import it.vfsfitvnm.vimusic.R
import it.vfsfitvnm.vimusic.cameraPermissionRequest
import it.vfsfitvnm.vimusic.facedetector.FaceDetectionActivity
import it.vfsfitvnm.vimusic.isPermissionGranted
import it.vfsfitvnm.vimusic.openPermissionSetting

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@RequiresApi(Build.VERSION_CODES.M)
class MoodView : ComponentActivity() {

    private val cameraPermission = android.Manifest.permission.CAMERA
    private var action = Action.FACE_DETECTION

    private val requestPermissionLauncher =
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (isGranted) {
                startCamera()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestCameraAndStart()
    }

    private fun requestCameraAndStart() {
        if (isPermissionGranted(cameraPermission)) {
            startCamera()
        } else {
            requestCameraPermission()
        }
    }

    private fun startCamera() {
        when (action) {
            Action.FACE_DETECTION -> FaceDetectionActivity.startActivity(this)
            else -> {}
        }
    }

    private fun requestCameraPermission() {
        when {
            shouldShowRequestPermissionRationale(cameraPermission) -> {
                cameraPermissionRequest(
                    positive = { openPermissionSetting() }
                )
            }
            else -> {
                requestPermissionLauncher.launch(cameraPermission)
            }
        }
    }
}