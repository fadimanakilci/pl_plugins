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
import 'package:flutter/services.dart';
import 'package:linfo_pl/src/network/domain/models/network_info_model.dart';

import '../../domain/sources/network_protocol.dart';
import '../../../utils/logging_util.dart';
import '../../../utils/method_channel_util.dart';
import '../../utils/network_protocol_parse_util.dart';
import '../../../business/services/linfo_pl_channel_interface.dart';

class LinfoPlNetworkChannelController implements LinfoPlChannelInterface {
  @visibleForTesting
  final MethodChannel methodChannel = MethodChannelUtil.methodChannel;

  @visibleForTesting
  final EventChannel eventChannel = MethodChannelUtil.networkEventChannel;

  Stream<List<NetworkProtocol>>? _onConnectivityChanged;

  @override
  Future<List<NetworkProtocol>> getEvent() async {
    List<NetworkProtocol> result = await methodChannel
        .invokeListMethod<String>('check')
        .then((value) => parseNetworkProtocols(value ?? []));

    LoggingUtil.info('CHECK LENGTH: ${result.length}');

    return result;
  }

  @override
  Future<List<NetworkProtocol>?> getEventChannel() async {
    onEventChannel.listen((event) {
      LoggingUtil.info('EventListen listen(): $event');
      NetworkProtocol item;
      for (item in event) {
        if(item == NetworkProtocol.wifi){
          getInfo();
        } else {
          LoggingUtil.info('ELSE: $item');
        }
      }
    })..onError((e) {
      LoggingUtil.info('EventListen onError(): $e');
    })..onDone(() {
      LoggingUtil.info('EventListen onDone()');
    });
  }

  // @override
  Stream<List<NetworkProtocol>> get onEventChannel {
    _onConnectivityChanged ??= eventChannel
        .receiveBroadcastStream()
        .map((dynamic result) => List<String>.from(result))
        .map(parseNetworkProtocols);

    return _onConnectivityChanged!;
  }

  Future getInfo() async {
    NetworkInfoModel? result = await methodChannel
        .invokeMapMethod<String, String?>('info')
        .then((value) => NetworkInfoModel.fromMap(value!));
        // .then((value) => parseNetworkProtocols(value ?? []));

    LoggingUtil.info('CHECK INFO: ${result?.toMap()}');

    return result;
  }
}
