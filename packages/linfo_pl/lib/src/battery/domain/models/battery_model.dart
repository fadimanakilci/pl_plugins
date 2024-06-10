/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'dart:ffi';

import 'package:linfo_pl/src/battery/domain/sources/charge_protocol.dart';
import 'package:linfo_pl/src/battery/domain/sources/health_protocol.dart';
import 'package:linfo_pl/src/battery/domain/sources/plug_protocol.dart';
import 'package:linfo_pl/src/battery/utils/charge_protocol_parse.dart';
import 'package:linfo_pl/src/battery/utils/health_protocol_parse.dart';
import 'package:linfo_pl/src/battery/utils/plug_protocol_parse.dart';

class BatteryModel {
  final int batteryLevel;
  final ChargeProtocol charging;
  final PlugProtocol plugged;
  final HealthProtocol health;
  final double temperature;
  final bool? isLow;
  final String? technology;
  final int level;
  final int scale;
  final bool present;
  final int voltage;
  final double? chargeCounter;
  final double? currentNow;
  final double? currentAverage;
  final int? capacity;
  final double? energyCounter;
  final int? status;
  final bool? isCharging;
  final String? chargeTime;

  BatteryModel({
    required this.batteryLevel,
    required this.charging,
    required this.plugged,
    required this.health,
    required this.temperature,
    required this.isLow,
    required this.technology,
    required this.level,
    required this.scale,
    required this.present,
    required this.voltage,
    required this.chargeCounter,
    required this.currentNow,
    required this.currentAverage,
    required this.capacity,
    required this.energyCounter,
    required this.status,
    required this.isCharging,
    required this.chargeTime,
  });

  static BatteryModel fromMap(Map? map) {
    return BatteryModel(
      batteryLevel: map?['batteryLevel'] ?? 0,
      charging: parseChargeProtocols(map?['charging']),
      plugged: parsePlugProtocols(map?['plugged']),
      health: parseHealthProtocols(map?['health']),
      temperature: map?['temperature'],
      isLow: map?['isLow'],
      technology: map?['technology'],
      level: map?['level'],
      scale: map?['scale'],
      present: map?['present'],
      voltage: map?['voltage'],
      chargeCounter: map?['chargeCounter'],
      currentNow: map?['currentNow'],
      currentAverage: map?['currentAverage'],
      capacity: map?['capacity'],
      energyCounter: map?['energyCounter'],
      status: map?['status'],
      isCharging: map?['isCharging'],
      chargeTime: map?['chargeTime'],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      "batteryLevel": batteryLevel,
      "charging": charging,
      "plugged": plugged,
      "health": health,
      "temperature": temperature,
      "isLow": isLow,
      "technology": technology,
      "level": level,
      "scale": scale,
      "present": present,
      "voltage": voltage,
      "chargeCounter": chargeCounter,
      "currentNow": currentNow,
      "currentAverage": currentAverage,
      "capacity": capacity,
      "energyCounter": energyCounter,
      "status": status,
      "isCharging": isCharging,
      "chargeTime": chargeTime,
    };
  }

  @override
  String toString() {
    return 'Battery Level: $batteryLevel,'
        '\t\t\t\tCharging: $charging,'
        '\t\t\t\tPlugged: $plugged,'
        '\t\t\t\tHealth: $health,'
        '\t\t\t\tTemperature: $temperature,'
        '\t\t\t\tIs Low: $isLow,'
        '\t\t\t\tTechnology: $technology,'
        '\t\t\t\tLevel/Scale: $level/$scale,'
        '\t\t\t\tPresent: $present,'
        '\t\t\t\tVoltage: $voltage,'
        // '\t\t\t\tCharge Counter: $chargeCounter µAh,'
        '\t\t\t\tCharge Counter: $chargeCounter Ah,'
        '\t\t\t\tCurrent Now/Average: $currentNow A/$currentAverage A,'
        '\t\t\t\tCapacity: $capacity,'
        // '\t\t\t\tEnergy Counter: $energyCounter nWh'
        '\t\t\t\tEnergy Counter: $energyCounter Wh,'
        '\t\t\t\tStatus: $status,'
        '\t\t\t\tIs Charging: $isCharging,'
        '\t\t\t\tCharge Time Remaining: $chargeTime,';
  }
}