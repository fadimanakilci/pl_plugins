/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/power/business/controllers/power_channel_controller.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import '../../../../power.dart';
import '../../domain/models/power_model.dart';

abstract class PowerPlatformInterface extends PlatformInterface {
  PowerPlatformInterface() : super(token: _token);

  static final Object _token = Object();

  static PowerPlatformInterface _instance = PowerChannelController();

  static PowerPlatformInterface get instance => _instance;

  static set instance(PowerPlatformInterface instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Stream<PowerModel> powerEventStream() {
    throw UnimplementedError(
      'powerEventStream has not been implemented.');
  }
}