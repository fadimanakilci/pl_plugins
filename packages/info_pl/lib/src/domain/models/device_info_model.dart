/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:info_pl/src/domain/models/display_info_model.dart';
import 'package:info_pl/src/domain/models/version_info_model.dart';

class DeviceInfo {
  final String deviceId;
  final String uuId;
  final String model;
  final String id;
  final String manufacturer;
  final String brand;
  final String user;
  final String type;
  final String board;
  final String host;
  final String fingerPrint;
  final String bootloader;
  final String device;
  final String display;
  final String hardware;
  final String product;
  final String tags;
  final bool isPhysicalDevice;
  final String simOperator;
  final String simOperatorName;
  final String simCountryIso;
  final String networkOperator;
  final String networkOperatorName;
  final String networkCountryIso;
  final String mmsUserAgent;
  final String mmsUAProfUrl;
  final int myUid;
  final String serialNumber;
  final int getElapsedCpuTime;
  final dynamic getExclusiveCores;
  final int getStartUptimeMillis;
  final int getStartElapsedRealtime;
  final String networkSpecifier;
  final bool isIsolated;
  final String manufacturerCode;
  final String typeAllocationCode;
  final int bluetoothUID;
  final int wifiUID;
  final String myProcessName;
  final int getStartRequestedUptimeMillis;
  final int getStartRequestedElapsedRealtime;
  final DisplayInfoModel displayMetrics;
  final VersionInfoModel version;
  final List supported32BitAbis;
  final List supported64BitAbis;
  final List supportedAbis;
  final List<Object?> systemFeatures;

  DeviceInfo({
    required this.deviceId,
    required this.uuId,
    required this.model,
    required this.id,
    required this.manufacturer,
    required this.brand,
    required this.user,
    required this.type,
    required this.board,
    required this.host,
    required this.fingerPrint,
    required this.bootloader,
    required this.device,
    required this.display,
    required this.hardware,
    required this.product,
    required this.tags,
    required this.isPhysicalDevice,
    required this.simOperator,
    required this.simOperatorName,
    required this.simCountryIso,
    required this.networkOperator,
    required this.networkOperatorName,
    required this.networkCountryIso,
    required this.mmsUserAgent,
    required this.mmsUAProfUrl,
    required this.myUid,
    required this.serialNumber,
    required this.getElapsedCpuTime,
    required this.getExclusiveCores,
    required this.getStartUptimeMillis,
    required this.getStartElapsedRealtime,
    required this.networkSpecifier,
    required this.isIsolated,
    required this.manufacturerCode,
    required this.typeAllocationCode,
    required this.bluetoothUID,
    required this.wifiUID,
    required this.myProcessName,
    required this.getStartRequestedUptimeMillis,
    required this.getStartRequestedElapsedRealtime,
    required this.displayMetrics,
    required this.version,
    required List<String> supported32BitAbis,
    required List<String> supported64BitAbis,
    required List<String> supportedAbis,
    required List<String> systemFeatures,
  })  : systemFeatures = List<String>.unmodifiable(systemFeatures),
        supported32BitAbis = List<String>.unmodifiable(supported32BitAbis),
        supported64BitAbis = List<String>.unmodifiable(supported64BitAbis),
        supportedAbis = List<String>.unmodifiable(supportedAbis);

  static DeviceInfo fromMap(Map info) {
    return DeviceInfo(
      deviceId: info['deviceId'],
      uuId: info['UUID'],
      model: info['model'],
      id: info['id'],
      manufacturer: info['manufacturer'],
      brand: info['brand'],
      user: info['user'],
      type: info['type'],
      board: info['board'],
      host: info['host'],
      fingerPrint: info['fingerprint'],
      bootloader: info['bootloader'],
      device: info['device'],
      display: info['display'],
      hardware: info['hardware'],
      product: info['product'],
      tags: info['tags'],
      isPhysicalDevice: info['isPhysicalDevice'],
      simOperator: info['simOperator'],
      simOperatorName: info['simOperatorName'],
      simCountryIso: info['simCountryIso'],
      networkOperator: info['networkOperator'],
      networkOperatorName: info['networkOperatorName'],
      networkCountryIso: info['networkCountryIso'],
      mmsUserAgent: info['mmsUserAgent'],
      mmsUAProfUrl: info['mmsUAProfUrl'],
      myUid: info['myUid'],
      serialNumber: info['serialNumber'],
      getElapsedCpuTime: info['getElapsedCpuTime'],
      getExclusiveCores: info['getExclusiveCores'],
      getStartUptimeMillis: info['getStartUptimeMillis'],
      getStartElapsedRealtime: info['getStartElapsedRealtime'],
      networkSpecifier: info['networkSpecifier'],
      isIsolated: info['isIsolated'],
      manufacturerCode: info['manufacturerCode'],
      typeAllocationCode: info['typeAllocationCode'],
      bluetoothUID: info['bluetoothUID'],
      wifiUID: info['wifiUID'],
      myProcessName: info['myProcessName'],
      getStartRequestedUptimeMillis: info['getStartRequestedUptimeMillis'],
      getStartRequestedElapsedRealtime:
          info['getStartRequestedElapsedRealtime'],
      displayMetrics: DisplayInfoModel.fromMap(info['displayMetrics']),
      version: VersionInfoModel.fromMap(info['version']),
      supported32BitAbis: _fromList(info['supported32BitAbis'] ?? <String>[]),
      supported64BitAbis: _fromList(info['supported64BitAbis'] ?? <String>[]),
      supportedAbis: _fromList(info['supportedAbis'] ?? []),
      systemFeatures: _fromList(info['systemFeatures'] ?? []),
    );
  }

  @override
  String toString() {
    return '------------------>>  DEVICE INFO <<------------------\n\n\n'
        '[DEVICE_ID]: $deviceId\n\n'
        '[UUID]: $uuId\n\n'
        '[MODEL]: $model\n\n'
        '[ID]: $id\n\n'
        '[MANUFACTURER]: $manufacturer\n\n'
        '[BRAND]: $brand\n\n'
        '[USER]: $user\n\n'
        '[TYPE]: $type\n\n'
        '[BOARD]: $board\n\n'
        '[HOST]: $host\n\n'
        '[FINGER_PRINT]: $fingerPrint\n\n'
        '[BOOTLOADER]: $bootloader\n\n'
        '[DEVICE]: $device\n\n'
        '[DISPLAY]: $display\n\n'
        '[HARDWARE]: $hardware\n\n'
        '[PRODUCT]: $product\n\n'
        '[TAGS]: $tags\n\n'
        '[IS_PHYSICAL_DEVICE]: $isPhysicalDevice\n\n'
        '[SIM_OPERATOR]: $simOperator\n\n'
        '[SIM_OPERATOR_NAME]: $simOperatorName\n\n'
        '[SIM_COUNTRY_ISO]: $simCountryIso\n\n'
        '[NETWORK_OPERATOR]: $networkOperator\n\n'
        '[NETWORK_OPERATOR_NAME]: $networkOperatorName\n\n'
        '[NETWORK_COUNTRY_ISO]: $networkCountryIso\n\n'
        '[MMS_USER_AGENT]: $mmsUserAgent\n\n'
        '[MMS_UA_PROF_URL]: $mmsUAProfUrl\n\n'
        '[MY_UID]: $myUid\n\n'
        '[SERIAL_NUMBER]: $serialNumber\n\n'
        '[ELAPSED_CPU_TIME]: $getElapsedCpuTime\n\n'
        '[EXCLUSIVE_CORES]: $getExclusiveCores\n\n'
        '[START_UPTIME_MILLIS]: $getStartUptimeMillis\n\n'
        '[START_ELAPSED_REALTIME]: $getStartElapsedRealtime\n\n'
        '[NETWORK_SPECIFIER]: $networkSpecifier\n\n'
        '[IS_ISOLATED]: $isIsolated\n\n'
        '[MANUFACTURER_CODE]: $manufacturerCode\n\n'
        '[TYPE_ALLOCATION_CODE]: $typeAllocationCode\n\n'
        '[BLUETOOTH_UID]: $bluetoothUID\n\n'
        '[WIFI_UID]: $wifiUID\n\n'
        '[MY_PROCESS_NAME]: $myProcessName\n\n'
        '[START_REQUESTED_UPTIME_MILLIS]: $getStartRequestedUptimeMillis\n\n'
        '[START_REQUESTED_ELAPSED_REALTIME]: $getStartRequestedElapsedRealtime\n\n'
        '[DISPLAY_METRICS]: ${displayMetrics.toString()}\n\n'
        '[VERSION]: ${version.toString()}\n\n'
        '[SUPPORTED_32_BIT_ABIS]: $supported32BitAbis\n\n'
        '[SUPPORTED_64_BIT_ABIS]: $supported64BitAbis\n\n'
        '[SUPPORTED_ABIS]: $supportedAbis\n\n'
        '[SYSTEM_FEATURES]: $systemFeatures\n\n';
  }

  static List<String> _fromList(List<dynamic> message) {
    final list = message.takeWhile((e) => e != null).toList();
    return List<String>.from(list.map((e) => '\n$e'));
  }
}
