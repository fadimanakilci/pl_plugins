/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:linfo_pl/src/business/services/linfo_pl_channel_interface.dart';
import '../../../utils/method_channel_util.dart';

class LinfoPlBluetoothChannelController implements LinfoPlChannelInterface {
  @visibleForTesting
  final MethodChannel methodChannel = MethodChannelUtil.methodChannel;

  @visibleForTesting
  final EventChannel eventChannel = MethodChannelUtil.bluetoothEventChannel;

  @override
  Future<void> getEventChannel() {
    throw UnimplementedError();
  }

  @override
  Future getEvent() {
    throw UnimplementedError();
  }

}
