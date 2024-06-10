/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

import 'headless_task.dart';
import '../utils/logging_util.dart';
import '../utils/method_channel_util.dart';

/// Headless Callback Dispatcher
/// Listen for background events from the platform portion of the plugin
@pragma('vm:entry-point')
void headlessCallbackDispatcher() async {
  const MethodChannel _headlessBackgroundChannel = MethodChannelUtil.headlessBackgroundChannel;

  WidgetsFlutterBinding.ensureInitialized();

  _headlessBackgroundChannel.setMethodCallHandler((call) async {
    final args = call.arguments;

    LoggingUtil.info('[_headlessCallbackDispatcher] Args: $args');

    // Run the headless-task.
    try {
      final callback = PluginUtilities.getCallbackFromHandle(
          CallbackHandle.fromRawHandle(args['callbackId']));
      if (callback == null) {
        LoggingUtil.warning(
            '[BackgroundFetch _headlessCallbackDispatcher] ERROR: Failed to get callback from handle: $args');
        return;
      }
      var task = HeadlessTask(args['task']['taskId'], args['task']['timeout']);
      // AccelerometerModel task = AccelerometerModel(x: args['task']['x']!,
      //     y: args['task']['y']!, z: args['task']['z']!);
      callback(task);
    } catch (e, stacktrace) {
      LoggingUtil.warning(
          "[BackgroundFetch _headlessCallbackDispather] ‼️ Callback error: ${e.toString()}");
      LoggingUtil.warning(stacktrace.toString());
    }
  });
  // Signal to native side that the client dispatcher is ready to receive events.
  _headlessBackgroundChannel.invokeMethod('initialized');
}