/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

package com.sparksign.info_pl

import android.content.Context
import android.content.pm.PackageManager
import android.view.WindowManager

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodChannel

/** InfoPlPlugin */
class InfoPlPlugin: FlutterPlugin {
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    setupMethodChannel(flutterPluginBinding.binaryMessenger, flutterPluginBinding.applicationContext)
  }

  private fun setupMethodChannel(messenger: BinaryMessenger, context: Context) {
    channel = MethodChannel(messenger, METHOD_CHANNEL_NAME)
    val packageManager: PackageManager = context.packageManager
    val windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val handler = MethodCallHandlerImpl(packageManager, windowManager, context)
    channel.setMethodCallHandler(handler)
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  companion object {
    private const val METHOD_CHANNEL_NAME =
      "com.sparksign.info_pl/methods"
  }
}
