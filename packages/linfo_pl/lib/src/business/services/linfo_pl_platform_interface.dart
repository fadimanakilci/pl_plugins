/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/business/controllers/linfo_pl_method_channel_controller.dart';
import 'package:linfo_pl/src/network/domain/sources/network_protocol.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import '../../../linfo_pl.dart';

abstract  class LinfoPlPlatformInterface extends PlatformInterface {
  LinfoPlPlatformInterface() : super(token: _token);

  static final Object _token = Object();

  static LinfoPlPlatformInterface _instance = LinfoPlMethodChannelController();

  static LinfoPlPlatformInterface get instance => _instance;

  static set instance(LinfoPlPlatformInterface instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  void getInitChannel() {
    throw UnimplementedError('getInitChannel() has not been implemented.');
  }

  Future<ResultEventModel> getEvent() {
    throw UnimplementedError('getEvent() has not been implemented.');
  }

  Future<void> getEventChannel() {
    throw UnimplementedError('getEventChannel() has not been implemented.');
  }

  // Stream<List<NetworkProtocol>> get onEventChannel {
  //   throw UnimplementedError(
  //       'get onEventChannel has not been implemented.');
  // }
}
