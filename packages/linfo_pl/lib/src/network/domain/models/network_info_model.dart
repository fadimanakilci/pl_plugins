/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

class NetworkInfoModel {
  final String wifiName;
  final String wifiBSSID;
  final String wifiIP;
  final String wifiIPv6;
  final String wifiSubmask;
  final String wifiBroadcast;
  final String wifiGateway;

  NetworkInfoModel({
    required this.wifiName,
    required this.wifiBSSID,
    required this.wifiIP,
    required this.wifiIPv6,
    required this.wifiSubmask,
    required this.wifiBroadcast,
    required this.wifiGateway,
  });

  static NetworkInfoModel fromMap(Map map) {
    return NetworkInfoModel(
      wifiName: map['wifiName'] ?? 'UNKNOWN',
      wifiBSSID: map['wifiBSSID'] ?? 'UNKNOWN',
      wifiIP: map['wifiIP'] ?? 'UNKNOWN',
      wifiIPv6: map['wifiIPv6'] ?? 'UNKNOWN',
      wifiSubmask: map['wifiSubmask'] ?? 'UNKNOWN',
      wifiBroadcast: map['wifiBroadcast'] ?? 'UNKNOWN',
      wifiGateway: map['wifiGateway'] ?? 'UNKNOWN',
    );
  }

  Map<String, dynamic> toMap() {
    return {
      "wifiName": wifiName,
      "wifiBSSID": wifiBSSID,
      "wifiIP": wifiIP,
      "wifiIPv6": wifiIPv6,
      "wifiSubmask": wifiSubmask,
      "wifiBroadcast": wifiBroadcast,
      "wifiGateway": wifiGateway,
    };
  }
}