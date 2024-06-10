/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';
import 'package:linfo_pl/src/network/business/services/network_platform_interface.dart';
import 'package:linfo_pl/src/utils/interval_util.dart';

import '../../domain/models/network_info_model.dart';
import '../../domain/sources/network_protocol.dart';
import '../../../background/headless_callback_dispatcher.dart';
import '../../../utils/logging_util.dart';
import '../../../utils/method_channel_util.dart';
import '../../utils/network_protocol_parse_util.dart';

class NetworkChannelController extends NetworkPlatformInterface {
  static Stream<List<NetworkProtocol>>? _networkEvents;
  static const Duration _interval = IntervalUtil.normalInterval;
  @visibleForTesting
  static const MethodChannel _methodChannel = MethodChannelUtil.methodChannel;
  @visibleForTesting
  static const EventChannel _channel = MethodChannelUtil.networkEventChannel;

  // Returns a broadcast stream of events from the device network at the
  // given sampling frequency.
  @override
  Stream<List<NetworkProtocol>> networkEventStream() {
    // TODO: Headless Task
    // int? callbackHandler =
    // PluginUtilities.getCallbackHandle(backgroundFetchHeadlessTask)?.toRawHandle();
    // if (callbackHandler == null) {
    //   LoggingUtil.warning('[BackgroundFetch registerHeadlessTask] ERROR: Failed '
    //       'to get callback id: Check whetever the callback is a op-level or static function');
    // }

    var microseconds = _interval.inMicroseconds;
    if (microseconds >= 1 && microseconds <= 3) {
      LoggingUtil.warning('The SamplingPeriod is currently set to $microsecondsμs, '
          'which is a reserved value in Android. Please consider changing it '
          'to either 0 or 4μs. See https://developer.android.com/reference/'
          'android/hardware/SensorManager#registerListener(android.hardware.'
          'SensorEventListener,%20android.hardware.Sensor,%20int) for more '
          'information');
      microseconds = 0;
    }

    // TODO: Headless Task
    // var args = [
    //   microseconds,
      // PluginUtilities.getCallbackHandle(headlessCallbackDispatcher)!.toRawHandle(),
      // callbackHandler ?? 0
    // ];
    // LoggingUtil.info('Args => $args');

    // _methodChannel
    //     .invokeMethod('registerHeadlessTask', args)
    //     .then((dynamic success) {
    //   LoggingUtil.fine('OLDUUUUUĞĞĞĞĞĞĞĞĞĞ');
    //   //   completer.complete(true);
    // }).catchError((error) {
    //   var message = error.toString();
    //   LoggingUtil.warning('[BackgroundFetch registerHeadlessTask] ‼️ $message');
    //   // completer.complete(false);
    // });

    // _methodChannel.invokeMethod('setAccelerometerPeriod', microseconds);

    _networkEvents ??= _channel
        .receiveBroadcastStream()
        .map((dynamic event) {
          final list = List<String>.from(event);
          LoggingUtil.info('Event Cast: $list');
          final List<NetworkProtocol> states = parseNetworkProtocols(list);

          NetworkProtocol item;
          for (item in states) {
            if(item == NetworkProtocol.wifi){
              getInfo();
            } else {
              LoggingUtil.info('ELSE: $item');
            }
          }

          return states;
        });
        // .map(parseNetworkProtocols);

    return _networkEvents!;
  }

  @override
  Future<List<NetworkProtocol>> getNetworkEvent() async {
    List<NetworkProtocol> result = await _methodChannel
        .invokeListMethod<String>('networkStatus')
        .then((value) => parseNetworkProtocols(value ?? []));

    LoggingUtil.info('NETWORK STATUS LENGTH: ${result.length}');

    return result;
  }

  @override
  Future<bool> requestOnMobile() async {
    bool result = await _methodChannel
        .invokeMethod<bool>('requestOnMobile').then((value) => value!);

    LoggingUtil.info('NETWORK REQUEST ON MOBILE: $result');

    return result;
  }

  Future getInfo() async {
    NetworkInfoModel? result = await _methodChannel
        .invokeMapMethod<String, String?>('networkInfo')
        .then((value) => NetworkInfoModel.fromMap(value!));
    // .then((value) => parseNetworkProtocols(value ?? []));

    LoggingUtil.info('CHECK INFO: ${result?.toMap()}');

    return result;
  }

  /// Signal to the OS that your fetch-event for the provided `taskId` is complete.
  ///
  /// You __MUST__ call `finish` in your fetch `callback` provided to [configure] in order to signal to the OS that your fetch action is complete. iOS provides only 30s of background-time for a fetch-event -- if you exceed this 30s, the OS will punish your app for spending too much time in the background.
  ///
  ///
  ///
  static void finish(String taskId) {
    _methodChannel.invokeMethod('finish', taskId);
  }
}
