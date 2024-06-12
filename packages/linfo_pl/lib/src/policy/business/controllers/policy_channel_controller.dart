/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © June 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, June 2024
 */

import 'package:flutter/services.dart';

import '../../../utils/logging_util.dart';
import '../../../utils/method_channel_util.dart';
import '../services/policy_platform_interface.dart';

class PolicyChannelController implements PolicyPlatformInterface {
  static const MethodChannel _methodChannel = MethodChannelUtil.methodChannel;

  @override
  Future<bool?> turnOffScreen() async {
    bool result = await _methodChannel.invokeMethod('turnOffScreen').then((value) => value);
    LoggingUtil.info('CHECK TURN OFF SCREEN: $result');
    return result;
  }
}
