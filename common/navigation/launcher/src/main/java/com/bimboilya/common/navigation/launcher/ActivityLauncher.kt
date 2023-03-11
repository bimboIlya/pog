package com.bimboilya.common.navigation.launcher

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.resume

object ActivityLauncher {

    private val requestCount = AtomicInteger()

    suspend fun <I, O> launchAndAwaitResult(contract: ActivityResultContract<I, O>, input: I): O = withContext(Dispatchers.Main.immediate) {
        val key = "activity_result_request#${requestCount.incrementAndGet()}"
        var isResultActivityLaunched = false
        var launcher: ActivityResultLauncher<I>? = null

        ActivityProvider.observeActivity()
            .mapLatest { activity ->
                suspendCancellableCoroutine { continuation ->
                    launcher = activity.activityResultRegistry.register(key, contract, continuation::resume)

                    if (!isResultActivityLaunched) {
                        launcher?.launch(input)
                        isResultActivityLaunched = true
                    }

                    continuation.invokeOnCancellation { launcher?.unregister() }
                }
            }
            .onCompletion { launcher?.unregister() }
            .first()
    }
}
