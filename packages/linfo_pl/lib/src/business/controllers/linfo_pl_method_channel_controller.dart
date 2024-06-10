/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/linfo_pl.dart';
import 'package:linfo_pl/src/bluetooth/business/controllers/linfo_pl_bluetooth_channel_controller.dart';
import 'package:linfo_pl/src/network/business/controllers/linfo_pl_network_channel_controller.dart';
import 'package:linfo_pl/src/business/services/linfo_pl_channel_interface.dart';
import 'package:linfo_pl/src/business/services/linfo_pl_platform_interface.dart';

import '../../network/domain/sources/network_protocol.dart';

class LinfoPlMethodChannelController implements LinfoPlPlatformInterface {
  late final LinfoPlChannelInterface linfoPlChannelInterface;

  // Activated All Channels
  @override
  void getInitChannel() {
    LinfoPlNetworkChannelController().getEventChannel();
  }

  // Get Channel Event
  @override
  Future<void> getEventChannel() async {
    linfoPlChannelInterface = LinfoPlBluetoothChannelController();
  }

  @override
  Future<ResultEventModel> getEvent() async {

    List<NetworkProtocol> network = await LinfoPlNetworkChannelController().getEvent();

    ResultEventModel result = ResultEventModel(
        network: network,
    );

    return result;
  }

  // @override
  // Stream<List<NetworkProtocol>> get onEventChannel => throw UnimplementedError();

}
