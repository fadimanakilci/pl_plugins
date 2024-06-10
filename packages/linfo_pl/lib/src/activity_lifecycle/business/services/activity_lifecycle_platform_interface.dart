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
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import '../controllers/activity_lifecycle_channel_controller.dart';

abstract class ActivityLifecyclePlatformInterface extends PlatformInterface {
  ActivityLifecyclePlatformInterface() : super(token: _token);

  static final Object _token = Object();

  static ActivityLifecyclePlatformInterface _instance = ActivityLifecycleChannelController();

  static ActivityLifecyclePlatformInterface get instance => _instance;

  static set instance(ActivityLifecyclePlatformInterface instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Stream<ActivityLifecycleProtocol> activityLifecycleEventStream() {
    throw UnimplementedError('activityLifecycleEventStream has not been implemented.');
  }

  @override
  Future<ActivityLifecycleProtocol> getActivityLifecycleEvent() {
    throw UnimplementedError('getActivityLifecycleEvent has not been implemented.');
  }
}