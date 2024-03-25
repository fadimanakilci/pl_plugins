
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:flutter/material.dart';
import 'package:info_pl/src/business/services/info_pl_platform_interface.dart';
import 'package:info_pl/src/domain/models/device_info_model.dart';
import 'package:logging/logging.dart';

export 'package:info_pl/src/domain/models/device_info_model.dart' show DeviceInfo;

class InfoPl {
  static InfoPl? _singleton;

  InfoPl._();

  factory InfoPl() {
    _singleton ??= InfoPl._();

    Logger.root.level = Level.ALL;
    Logger.root.onRecord.listen((record) {
      debugPrint('[${record.level.name}] - ${record.time}: ${record.message}');
    });

    return _singleton!;
  }

  Future<DeviceInfo?> init() {
    return InfoPlPlatformInterface.instance.init();
  }
}
