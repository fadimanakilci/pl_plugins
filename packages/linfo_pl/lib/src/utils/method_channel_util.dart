/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:flutter/services.dart';

class MethodChannelUtil {
  static const String pluginPath = 'com.sparksign.linfo_pl';

  static const MethodChannel methodChannel =
  MethodChannel('$pluginPath/methods');

  static const MethodChannel headlessBackgroundChannel =
  MethodChannel('$pluginPath/headless', JSONMethodCodec());

  static const EventChannel networkEventChannel =
  EventChannel('$pluginPath/network');

  static const EventChannel bluetoothEventChannel =
  EventChannel('$pluginPath/bluetooth');

  static const EventChannel batteryEventChannel =
  EventChannel('$pluginPath/battery');

  static const EventChannel powerEventChannel =
  EventChannel('$pluginPath/power');

  static const EventChannel powerThermalEventChannel =
  EventChannel('$pluginPath/powerThermal');

  static const EventChannel windowEventChannel =
  EventChannel('$pluginPath/window');

  static const EventChannel orientationEventChannel =
  EventChannel('$pluginPath/orientation');
}
