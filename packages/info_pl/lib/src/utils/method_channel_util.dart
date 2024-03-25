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
  static const String pluginPath = 'com.sparksign.info_pl';

  static const MethodChannel methodChannel =
  MethodChannel('$pluginPath/methods');

  static const EventChannel accelerometerEventChannel =
  EventChannel('$pluginPath/infos');
}