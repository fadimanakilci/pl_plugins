/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/power/domain/sources/thermal_status_protocol.dart';
import 'package:linfo_pl/src/power/utils/thermal_status_protocol_parse.dart';

class PowerModel {
  final bool? interactive;

  PowerModel({
    required this.interactive,
  });

  static PowerModel fromMap(Map? map) {
    return PowerModel(
        interactive: map?['interactive'],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      "interactive": interactive,
    };
  }

  @override
  String toString() {
    return 'Interactive: $interactive,';
        // '\t\t\t\tThermal Status: $thermalStatus';
  }
}