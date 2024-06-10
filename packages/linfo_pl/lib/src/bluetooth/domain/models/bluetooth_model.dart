/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

class BluetoothModel {
  final String data;

  BluetoothModel({
    required this.data,
  });

  static BluetoothModel fromMap(Map map) {
    return BluetoothModel(
        data: map['map'],
    );
  }
}