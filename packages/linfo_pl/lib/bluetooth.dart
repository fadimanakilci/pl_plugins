/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:linfo_pl/src/bluetooth/domain/sources/bluetooth_protocol.dart';
import 'package:linfo_pl/src/bluetooth/presentation/controllers/bluetooth_controller.dart';

export 'package:linfo_pl/src/bluetooth/domain/sources/bluetooth_protocol.dart' show BluetoothProtocol;

final _bluetooth = BluetoothController();

/// Returns a broadcast stream of events from the device bluetooth at the
/// given sampling frequency.
@override
Stream<BluetoothProtocol> bluetoothEventStream() {
  return _bluetooth.bluetoothEventStream();
}

@override
Future<BluetoothProtocol?> getBluetoothEvent() {
  return _bluetooth.getBluetoothEvent();
}

/// Stops returning the broadcast stream of events from the device
/// bluetooth at the given sampling frequency.
@override
Future<bool> bluetoothStateOn() {
  return _bluetooth.bluetoothStateOn();
}

@override
Future<bool> bluetoothStateOff() {
  return _bluetooth.bluetoothStateOff();
}
