package com.bimboilya.common.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class CoroutineTestExtension(
    val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher(),
    val scope: TestCoroutineScope = TestCoroutineScope(dispatcher),
) : BeforeEachCallback,
    AfterEachCallback,
    TestCoroutineScope by scope {

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(dispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        dispatcher.cleanupTestCoroutines()
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}