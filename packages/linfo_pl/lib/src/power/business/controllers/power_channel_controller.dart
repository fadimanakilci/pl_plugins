/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:flutter/services.dart';
import 'package:linfo_pl/src/power/business/services/power_platform_interface.dart';
import 'package:linfo_pl/src/power/domain/models/power_model.dart';
import 'package:linfo_pl/src/utils/interval_util.dart';
import 'package:linfo_pl/src/utils/method_channel_util.dart';

import '../../../utils/logging_util.dart';

class PowerChannelController extends PowerPlatformInterface {
  static Stream<PowerModel>? _powerEvent;
  static const Duration _interval = IntervalUtil.normalInterval;
  static const MethodChannel _methodChannel = MethodChannelUtil.methodChannel;
  static const EventChannel _channel = MethodChannelUtil.powerEventChannel;

  @override
  Stream<PowerModel> powerEventStream() {
    var microseconds = _interval.inMicroseconds;
    if (microseconds >= 1 && microseconds <= 3) {
      microseconds = 0;
    }

    _powerEvent ??= _channel
        .receiveBroadcastStream().map((event) {
      LoggingUtil.info('Power Event Cast: $event');

      return PowerModel.fromMap(event);
    });

    return _powerEvent!;
  }
}