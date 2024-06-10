/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import '../../../../power.dart';
import '../controllers/power_thermal_channel_controller.dart';

abstract class PowerThermalPlatformInterface extends PlatformInterface {
  PowerThermalPlatformInterface() : super(token: _token);

  static final Object _token = Object();

  static PowerThermalPlatformInterface _instance = PowerThermalChannelController();

  static PowerThermalPlatformInterface get instance => _instance;

  static set instance(PowerThermalPlatformInterface instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Stream<ThermalStatusProtocol> powerThermalEventStream() {
    throw UnimplementedError(
        'powerThermalEventStream has not been implemented.');
  }

  Future<ThermalStatusProtocol> getPowerThermalStatus() {
    throw UnimplementedError(
        'getPowerThermalStatus has not been implemented.');
  }
}