/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/power.dart';

import '../../business/services/power_platform_interface.dart';
import '../../domain/models/power_model.dart';

class PowerController extends PowerPlatformInterface {
  static PowerController? _singleton;

  PowerController._();

  factory PowerController() => _singleton ??= PowerController._();

  static PowerPlatformInterface get _platform => PowerPlatformInterface.instance;

  @override
  Stream<PowerModel> powerEventStream() {
    return _platform.powerEventStream();
  }

}