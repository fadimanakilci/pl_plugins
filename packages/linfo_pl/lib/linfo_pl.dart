
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

library linfo_pl;

import 'package:flutter/material.dart';
import 'package:linfo_pl/src/business/models/result_event_model.dart';
import 'package:linfo_pl/src/business/services/linfo_pl_platform_interface.dart';
import 'package:linfo_pl/src/network/domain/sources/network_protocol.dart';
import 'package:logging/logging.dart';

import 'linfo_pl.dart';

export 'package:linfo_pl/src/business/models/result_event_model.dart' show ResultEventModel;

class LinfoPl {
  static LinfoPl? _singleton;

  LinfoPl._();

  factory LinfoPl() {
    _singleton ??= LinfoPl._();

    // LinfoPlPlatformInterface.instance.getInitChannel();

    Logger.root.level = Level.ALL;
    Logger.root.onRecord.listen((record) {
      debugPrint('[${record.level.name}] - ${record.time}: ${record.message}');
    });

    return _singleton!;
  }

  void init() {
    return LinfoPlPlatformInterface.instance.getInitChannel();
  }

  Future<void> getEventChannel() {
    return LinfoPlPlatformInterface.instance.getEventChannel();
  }

  Future<ResultEventModel> getEvent() {
    return LinfoPlPlatformInterface.instance.getEvent();
  }

  // Stream<List<NetworkProtocol>> get onEventChannel {
  //   return LinfoPlPlatformInterface.instance.onEventChannel;
  // }
}

