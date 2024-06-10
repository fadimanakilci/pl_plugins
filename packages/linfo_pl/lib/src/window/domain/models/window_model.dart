/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

class WindowModel {
  bool? screenOn;

  WindowModel({
    required this.screenOn,
  });

  static WindowModel fromMap(Map? map) {
    return WindowModel(
      screenOn: map?['screenOn'],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      "screenOn": screenOn,
    };
  }

  @override
  String toString() {
    return 'Keep Screen On: $screenOn,';
    // '\t\t\t\tThermal Status: $thermalStatus';
  }
}