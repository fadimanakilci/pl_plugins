/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import '../../../../power.dart';
import '../../business/services/power_thermal_platform_interface.dart';

class PowerThermalController extends PowerThermalPlatformInterface {
  static PowerThermalController? _singleton;

  PowerThermalController._();

  factory PowerThermalController() => _singleton ??= PowerThermalController._();

  static PowerThermalPlatformInterface get _platform => PowerThermalPlatformInterface.instance;

  @override
  Stream<ThermalStatusProtocol> powerThermalEventStream() {
    return _platform.powerThermalEventStream();
  }

  @override
  Future<ThermalStatusProtocol> getPowerThermalStatus() {
    return _platform.getPowerThermalStatus();
  }

}