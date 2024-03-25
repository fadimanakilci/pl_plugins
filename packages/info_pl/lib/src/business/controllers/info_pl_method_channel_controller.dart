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
import 'package:info_pl/src/business/services/info_pl_platform_interface.dart';
import 'package:info_pl/src/domain/models/device_info_model.dart';
import 'package:info_pl/src/utils/method_channel_util.dart';

class InfoPlMethodChannelController implements InfoPlPlatformInterface {
  @visibleForTesting
  final MethodChannel methodChannel = MethodChannelUtil.methodChannel;
  @visibleForTesting
  static DeviceInfo? deviceInfoModel;

  @override
  Future<DeviceInfo?> init() async {
    WidgetsFlutterBinding.ensureInitialized();

    Completer completer = Completer<DeviceInfo?>();

    final Map<dynamic, dynamic>? info = await methodChannel.invokeMethod('getDeviceInfo');
    deviceInfoModel = info == null ? null : DeviceInfo.fromMap(info);

    completer.complete(deviceInfoModel);
    return completer.future as Future<DeviceInfo?>;
  }
}