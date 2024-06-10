/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/network/business/controllers/network_channel_controller.dart';

import '../../../background/headless_task.dart';
import '../../../utils/logging_util.dart';

int _headlessCount = 0;
int _count = 0;

// [Android-only] This "Headless Task" is run when the Android app is terminated with `enableHeadless: true`
// Be sure to annotate your callback function to avoid issues in release mode on Flutter >= 3.3.0
@pragma('vm:entry-point')
void backgroundFetchHeadlessTask(HeadlessTask task) async {
  String taskId = task.taskId;
  bool isTimeout = task.timeout;
  if (isTimeout) {
    // This task has exceeded its allowed running-time.
    // You must stop what you're doing and immediately .finish(taskId)
    LoggingUtil.warning("[BackgroundFetch] Headless task timed-out: $taskId");
    NetworkChannelController.finish(taskId);
    return;
  }
  LoggingUtil.fine('[BackgroundFetch] Headless event received.');
  // Do your work here...
  print("[BackgroundFetch] HeadlessTask: $taskId");

  // try {
  //   var location = await bg.BackgroundGeolocation.getCurrentPosition(
  //       samples: 2,
  //       extras: {
  //         "event": "background-fetch",
  //         "headless": true
  //       }
  //   );
  //   print("[location] $location");
  // } catch(error) {
  //   print("[location] ERROR: $error");
  // }
  //
  ///
  // SharedPreferences prefs = await SharedPreferences.getInstance();
  // if (prefs.get("fetch-count") != null) {
  //   _headlessCount = prefs.getInt("fetch-count")!;
  // }
  // prefs.setInt("fetch-count", ++_headlessCount);
  // print('[BackgroundFetch] count: $_headlessCount');
  //
  // if (taskId == 'flutter_background_fetch') {
  //   AccelerometerSensorPl.scheduleTask(TaskConfig(
  //       taskId: "com.transistorsoft.customtask",
  //       delay: 5000,
  //       periodic: false,
  //       forceAlarmManager: false,
  //       stopOnTerminate: false,
  //       enableHeadless: true
  //   ));
  // }
  //
  // AccelerometerSensorPl.finish(taskId);
}