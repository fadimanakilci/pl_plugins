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
import 'package:linfo_pl/src/activity_lifecycle/presentation/controllers/activity_lifecycle_controller.dart';

import 'activity_lifecycle.dart';

export 'package:linfo_pl/src/activity_lifecycle/domain/sources/activity_lifecycle_protocol.dart' show ActivityLifecycleProtocol;

final _lifecycle = ActivityLifecycleController();

@override
Stream<ActivityLifecycleProtocol> activityLifecycleEventStream() {
  return _lifecycle.activityLifecycleEventStream();
}

@override
Future<ActivityLifecycleProtocol> getActivityLifecycleEvent() {
  return _lifecycle.getActivityLifecycleEvent();
}
