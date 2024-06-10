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

abstract class LinfoPlChannelInterface {

  Future getEvent() {
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