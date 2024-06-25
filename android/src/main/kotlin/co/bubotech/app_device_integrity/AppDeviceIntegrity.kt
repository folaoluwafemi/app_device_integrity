//package co.bubotech.app_device_integrity
//
//import com.google.android.gms.tasks.Task
//import com.google.android.play.core.integrity.IntegrityTokenResponse
//import com.google.android.play.core.integrity.IntegrityManager
//import com.google.android.play.core.integrity.IntegrityManagerFactory
//import com.google.android.play.core.integrity.IntegrityTokenRequest
//import android.content.Context
//import android.util.Base64
//
//class AppDeviceIntegrity(context: Context, cloudProjectNumber: Long) {
//
//    //    var nonceBytes = ByteArray(40)
////    var randomized = SecureRandom().nextBytes(nonceBytes)
//    var nonce = Base64.encodeToString(ByteArray(40),  Base64.NO_WRAP)
//
//    // Create an instance of a manager.
//    val integrityManager: IntegrityManager = IntegrityManagerFactory.create(context)
//
//    // Request the integrity token by providing a nonce.
//    val integrityTokenResponse: Task<IntegrityTokenResponse> = integrityManager.requestIntegrityToken(
//        IntegrityTokenRequest.builder()
//            .setNonce(nonce)
//            .setCloudProjectNumber(cloudProjectNumber)
//            .build())
//
//}


package co.bubotech.app_device_integrity

import android.content.Context
import android.util.Base64
import com.google.android.gms.tasks.Task
import com.google.android.play.core.integrity.IntegrityTokenResponse
import com.google.android.play.core.integrity.IntegrityManager
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.IntegrityTokenRequest

class AppDeviceIntegrity(context: Context, cloudProjectNumber: Long, nonceHex: String) {

    // Convert hex string to byte array
    private fun hexStringToByteArray(hexString: String): ByteArray {
        val len = hexString.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(hexString[i], 16) shl 4) + Character.digit(hexString[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }

    // Convert the received hex nonce to a Base64 URL-safe encoded string
    private val nonce: String = Base64.encodeToString(hexStringToByteArray(nonceHex), Base64.URL_SAFE or Base64.NO_WRAP)

    // Create an instance of a manager
    private val integrityManager: IntegrityManager = IntegrityManagerFactory.create(context)

    // Request the integrity token by providing a nonce
    val integrityTokenResponse: Task<IntegrityTokenResponse> = integrityManager.requestIntegrityToken(
            IntegrityTokenRequest.builder()
                    .setNonce(nonce)
                    .setCloudProjectNumber(cloudProjectNumber)
                    .build()
    )
}
