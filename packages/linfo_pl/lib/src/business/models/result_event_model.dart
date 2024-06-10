/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import '../../network/domain/sources/network_protocol.dart';

class ResultEventModel {
  final List<NetworkProtocol> network;

  ResultEventModel({
    required this.network,
  });

  static ResultEventModel fromMap(Map map) {
    return ResultEventModel(
      network: map['network'],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      "network": network,
    };
  }
}