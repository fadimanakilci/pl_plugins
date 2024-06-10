/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/activity_lifecycle/domain/sources/activity_lifecycle_protocol.dart';

import '../../business/services/activity_lifecycle_platform_interface.dart';

class ActivityLifecycleController extends ActivityLifecyclePlatformInterface {
  static ActivityLifecycleController? _singleton;

  ActivityLifecycleController._();

  factory ActivityLifecycleController() => _singleton ??= ActivityLifecycleController._();

  static ActivityLifecyclePlatformInterface get _platform => ActivityLifecyclePlatformInterface.instance;

  @override
  Stream<ActivityLifecycleProtocol> activityLifecycleEventStream() {
    return _platform.activityLifecycleEventStream();
  }

  @override
  Future<ActivityLifecycleProtocol> getActivityLifecycleEvent() {
    return _platform.getActivityLifecycleEvent();
  }
}