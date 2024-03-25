/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:info_pl/src/domain/models/device_info_model.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import '../controllers/info_pl_method_channel_controller.dart';

interface class InfoPlPlatformInterface extends PlatformInterface {
  InfoPlPlatformInterface() : super(token: _token);

  static final Object _token = Object();

  static InfoPlPlatformInterface _instance = InfoPlMethodChannelController();

  static InfoPlPlatformInterface get instance => _instance;

  static set instance(InfoPlPlatformInterface instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<DeviceInfo?> init() {
    throw UnimplementedError('init() has not been implemented.');
  }
}