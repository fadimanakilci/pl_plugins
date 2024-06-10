/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/activity_lifecycle.dart';

ActivityLifecycleProtocol parseActivityLifecycleProtocols(String state) {
  switch (state.trim()) {
    case 'any':
      return ActivityLifecycleProtocol.any;
    case 'initialized':
      return ActivityLifecycleProtocol.initialized;
    case 'create':
      return ActivityLifecycleProtocol.created;
    case 'started':
      return ActivityLifecycleProtocol.started;
    case 'resumed':
      return ActivityLifecycleProtocol.resumed;
    case 'paused':
      return ActivityLifecycleProtocol.paused;
    case 'stopped':
      return ActivityLifecycleProtocol.stopped;
    case 'saveInstanceState':
      return ActivityLifecycleProtocol.saveInstanceState;
    case 'destroyed':
      return ActivityLifecycleProtocol.destroyed;
    default:
      return ActivityLifecycleProtocol.unknown;
  }
}