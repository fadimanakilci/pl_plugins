/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:linfo_pl/activity_lifecycle.dart';
import 'package:linfo_pl/linfo_pl.dart';
import 'package:linfo_pl/network.dart';
import 'package:linfo_pl/bluetooth.dart';
import 'package:linfo_pl/battery.dart';
import 'package:linfo_pl/power.dart';
import 'package:linfo_pl/window.dart';
import 'package:linfo_pl/policy.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _linfoPlPlugin = LinfoPl();

  List<NetworkProtocol> _connectionStatus = [NetworkProtocol.none];
  BluetoothProtocol _bluetoothState = BluetoothProtocol.unknown;
  bool? _wakeLockState;
  BatteryModel? _batteryState;
  PowerModel? _powerState;
  ThermalStatusProtocol? _powerThermalState;
  WindowModel? _windowState;

  int _batteryLevel = 0;
  ChargeProtocol _charging = ChargeProtocol.unknown;

  ActivityLifecycleProtocol _activityLifecycleState = ActivityLifecycleProtocol.unknown;

  late final StreamSubscription<List<NetworkProtocol>> _networkSubscription;
  late final StreamSubscription<BluetoothProtocol> _bluetoothSubscription;
  late final StreamSubscription<PowerModel> _powerSubscription;
  late final StreamSubscription<ThermalStatusProtocol> _powerThermalSubscription;
  late final StreamSubscription<WindowModel?> _windowSubscription;

  late final StreamSubscription<BatteryModel>? _batteryEventSubscription;
  late final StreamSubscription<int> _batteryLevelSubscription;
  late final StreamSubscription<ChargeProtocol> _chargingSubscription;

  late final StreamSubscription<ActivityLifecycleProtocol> _activityLifecycleSubscription;

  @override
  void initState() {
    super.initState();

    const int maxValue = -1 >>> 1;
    debugPrint('Int: $maxValue');

    initPlatformState();
  }

  @override
  void dispose() {
    _networkSubscription.cancel();
    _bluetoothSubscription.cancel();
    _powerSubscription.cancel();
    _powerThermalSubscription.cancel();

    _batteryEventSubscription?.cancel();
    _batteryLevelSubscription.cancel();
    _chargingSubscription.cancel();

    // _activityLifecycleSubscription.cancel();
  }

  Future<void> initPlatformState() async {
    try {
      // _linfoPlPlugin.init();
      // ResultEventModel result = await _linfoPlPlugin.getEvent();
      // debugPrint('RESULT: ${result.toMap()}');
      _networkEventStream();
      _bluetoothEventStream();
      _batteryEventStream();
      _powerEventStream();
      _powerThermalEventStream();
      _activityLifecycleEventStream();
      _windowEventStream();

      List<NetworkProtocol>? networkEvent
      = await getNetworkEvent();
      BluetoothProtocol? bluetoothEvent
      = await getBluetoothEvent();
      ActivityLifecycleProtocol activityLifecycleState
      = await getActivityLifecycleEvent();
      int? batteryLevel = await getBatteryLevel();

      ThermalStatusProtocol thermalStatus
      = await getPowerThermalStatus();

      debugPrint('EventListen listen(): '
          '\n\t$networkEvent\n\t$bluetoothEvent');
      debugPrint('\t$activityLifecycleState');
      debugPrint("Battery Level: $batteryLevel");
      debugPrint("Thermal Status: $thermalStatus");
    } on PlatformException {

    }

    if (!mounted) return;

    setState(() {

    });
  }

  void _networkEventStream() {
    _networkSubscription = networkEventStream().listen((List<NetworkProtocol> event) {
      debugPrint('EventStreamListen listen(): $event');

      setState(() {
        _connectionStatus = event;
      });

      NetworkProtocol networkProtocol;
      for (networkProtocol in event) {
        if(networkProtocol == NetworkProtocol.wifi || networkProtocol == NetworkProtocol.mobile){
          debugPrint('Network Connection Status: $networkProtocol');
        } else if(networkProtocol == NetworkProtocol.none) {
          debugPrint('Network Connection None: $networkProtocol');
        } else {
          debugPrint('Network Connection Error: $networkProtocol');
        }
      }
    });
  }

  void _bluetoothEventStream() {
    _bluetoothSubscription = bluetoothEventStream().listen((BluetoothProtocol event) {
      debugPrint('EventStreamListen listen(): $event');

      setState(() {
        _bluetoothState = event;
      });

      if(event == BluetoothProtocol.turningOn || event == BluetoothProtocol.on){
        debugPrint('Bluetooth On Connection State: $event');
      } else if(event == BluetoothProtocol.turningOff || event == BluetoothProtocol.off) {
        debugPrint('Bluetooth Off Connection State: $event');
      } else if(event == BluetoothProtocol.unknown) {
        debugPrint('Bluetooth Connection Unknown: $event');
      } else {
        debugPrint('Bluetooth Connection Error: $event');
      }
    });
  }

  void _batteryEventStream() {
    _batteryEventSubscription = batteryEventStream().listen((BatteryModel event) {
      debugPrint('EventStreamListen listen(): ${event.toMap()}');

      setState(() {
        _batteryState = event;
      });
    });
  }

  void _powerEventStream() {
    _powerSubscription = powerEventStream().listen((PowerModel event) {
      debugPrint('Power EventStreamListen listen(): ${event.toMap()}');

      setState(() {
        _powerState = event;
      });
    });
  }

  void _powerThermalEventStream() {
    _powerThermalSubscription = powerThermalEventStream().listen((ThermalStatusProtocol event) {
      debugPrint('Power Thermal EventStreamListen listen(): ${event.name}');

      setState(() {
        _powerThermalState = event;
      });
    });
  }

  void _activityLifecycleEventStream() {
    _activityLifecycleSubscription = activityLifecycleEventStream()
        .listen((ActivityLifecycleProtocol event) {
      debugPrint('ActivityLifecycleEventListenThen listen(): $event');

      setState(() {
        _activityLifecycleState = event;
      });
    });
  }

  void _windowEventStream() {
    _windowSubscription = screenEventStream()
        .listen((WindowModel? event) {
      debugPrint('WindowEventListenThen listen(): $event');

      setState(() {
        _windowState = event;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        appBar: AppBar(
          centerTitle: true,
          title: const Text('LinfoPL'),
        ),
        body: SingleChildScrollView(
          child: Column(
            children: [
              // Activity
              Padding(
                padding: const EdgeInsets.all(20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    const Text("ACTIVITY:"),
                    Text(_activityLifecycleState.name.toUpperCase()),
                  ],
                ),
              ),
              const Divider(),
              // Power
              Padding(
                padding: const EdgeInsets.all(20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    const Text("POWER:"),
                    const SizedBox(width: 10,),
                    Expanded(child: Text('${_powerThermalState?.name.toUpperCase()} - $_powerState')),
                  ],
                ),
              ),
              const Divider(),
              // Battery
              Padding(
                padding: const EdgeInsets.all(20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    const Text("BATTERY:"),
                    const SizedBox(width: 10,),
                    // Text(
                    //     "${_batteryState?.health.name.toUpperCase()}"
                    //         " - ${_batteryState?.temperature} °C"
                    //         " - ${_batteryState?.charging.name.toUpperCase()}"
                    //         " - ${_batteryState?.plugged.name.toUpperCase()}"
                    //         " - ${_batteryState?.batteryLevel}"
                    //         " - ${_batteryState?.isLow.toString().toUpperCase()}"
                    // ),
                    Expanded(child: Text(_batteryState.toString())),
                  ],
                ),
              ),
              const Divider(),
              // WakeLock
              Padding(
                padding: const EdgeInsets.symmetric(
                    vertical: 5,
                    horizontal: 20,
                ),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text("WakeLock: ${_wakeLockState.toString().toUpperCase()}"),
                    const Spacer(),
                    TextButton(
                      onPressed: () async {
                        bool? result = await schedulerWakeLock(1);
                        debugPrint("WakeLock Scheduler result: $result");
                      },
                      style: TextButton.styleFrom(
                        backgroundColor: Colors.grey.shade300,
                        textStyle: const TextStyle(color: Colors.black)
                      ),
                      child: const Text('SCHEDULER'),
                    ),
                    TextButton(
                      onPressed: () async {
                        await periodicWakeLock();
                        // await periodicWakeLock(
                        //   repeat: 1,
                        //   minutes: 2,
                        //   waitMinutes: 1,
                        // );
                        debugPrint("WakeLock Periodic End");
                      },
                      style: TextButton.styleFrom(
                        backgroundColor: Colors.grey.shade300,
                        textStyle: const TextStyle(color: Colors.black)
                      ),
                      child: const Text('PERIODIC'),
                    ),
                    Switch(
                      value: _wakeLockState ?? false,
                      activeColor: Colors.deepPurpleAccent,
                      onChanged: (bool value) async {
                        debugPrint("WakeLock switch value: $value"
                            "\nWakeLock state: $_wakeLockState");
                        if(value) {
                          bool result = await enableWakeLock();
                          result ? _wakeLockState = result : {};
                          debugPrint("WakeLock switched value: $value"
                              "\nWakeLock state: $_wakeLockState");
                        } else {
                          bool result = await disableWakeLock();
                          _wakeLockState = result;
                          debugPrint("WakeLock switched value: $value"
                              "\nWakeLock state: $_wakeLockState");
                        }
                        setState(() {
                          // light = value;
                        });
                      },
                    ),
                  ],
                ),
              ),
              const Divider(),
              // Window
              Padding(
                padding: const EdgeInsets.symmetric(vertical: 5,
                    horizontal: 20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text("Window: ${_windowState.toString().toUpperCase()}"),
                    Switch(
                      value: _windowState?.screenOn ?? false,
                      activeColor: Colors.deepPurpleAccent,
                      onChanged: (bool value) async {
                        debugPrint("Window switch value: $value"
                            "\nWindow state: $_windowState");
                        if(value) {
                          bool result = await keepScreenOn();
                          _windowState?.screenOn = result;
                          debugPrint("Window switched value: $value"
                              "\nWindow keep state: $_windowState");
                        } else {
                          bool result = await discardScreenOn();
                          _windowState?.screenOn = result;
                          debugPrint("Window switched value: $value"
                              "\nWindow discard state: $_windowState");
                        }
                        setState(() {
                          // light = value;
                        });
                      },
                    ),
                  ],
                ),
              ),
              const Divider(),
              // Policy
              Padding(
                padding: const EdgeInsets.symmetric(vertical: 5,
                    horizontal: 20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text("Policy: ${_windowState.toString().toUpperCase()}"),
                    TextButton(
                      onPressed: () async {
                        await turnOffScreen();
                        debugPrint("Turn Off Screen End");
                      },
                      style: TextButton.styleFrom(
                          backgroundColor: Colors.grey.shade300,
                          textStyle: const TextStyle(color: Colors.black)
                      ),
                      child: const Text('TURN OFF'),
                    ),
                  ],
                ),
              ),
              const Divider(),
              // Bluetooth
              Padding(
                padding: const EdgeInsets.symmetric(vertical: 5,
                    horizontal: 20),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text("BLUETOOTH: ${_bluetoothState.name.toUpperCase()}"),
                    Switch(
                      value: _bluetoothState == BluetoothProtocol.on
                          ? true : false,
                      activeColor: Colors.deepPurpleAccent,
                      onChanged: (bool value) async{
                        debugPrint("Bluetooth switch value: $value"
                            "\nBluetooth state: ${_bluetoothState.name}");
                        if(value) {
                          bool result = await bluetoothStateOn();
                          result ? _bluetoothState = BluetoothProtocol.on : {};
                          debugPrint("Bluetooth switched value: $value"
                              "\nBluetooth state: ${_bluetoothState.name}");
                        } else {
                          bool result = await bluetoothStateOff();
                          result ? _bluetoothState = BluetoothProtocol.off : {};
                          debugPrint("Bluetooth switched value: $value"
                              "\nBluetooth state: ${_bluetoothState.name}");
                        }
                        setState(() {
                          // light = value;
                        });
                      },
                    ),
                  ],
                ),
              ),
              const Divider(),
              // Network
              SizedBox(
                height: 50,
                child: ListView(
                  physics: const NeverScrollableScrollPhysics(),
                  padding: const EdgeInsets.symmetric(vertical: 5, horizontal: 20),
                  children: List.generate(
                    _connectionStatus.length,
                    (index) => Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(
                          "NETWORK: ${_connectionStatus[index].name.toUpperCase()}",
                        ),
                        Switch(
                          value: _connectionStatus[index] == NetworkProtocol.mobile
                              ? true : false,
                          activeColor: Colors.deepPurpleAccent,
                          onChanged: (bool value) async {
                            debugPrint("Network request on mobile switch value: $value"
                                "\Network state: ${_connectionStatus[index].name}");
                            if(value) {
                              bool result = await requestOnMobile();
                              // result ? _bluetoothState = BluetoothProtocol.on : {};
                              debugPrint("Network switched value: $result - $value"
                                  "\Network state: ${_connectionStatus[index].name}");
                            } else {
                              // bool result = await bluetoothStateOff();
                              // result ? _bluetoothState = BluetoothProtocol.off : {};
                              debugPrint("Network switched value: $value"
                                  "\Network state: ${_connectionStatus[index].name}");
                            }
                            setState(() {
                              // light = value;
                            });
                          },
                        ),
                        Switch(
                          value: (_connectionStatus[index] == NetworkProtocol.mobile
                              || _connectionStatus[index] == NetworkProtocol.wifi)
                              ? true : false,
                          activeColor: Colors.deepPurpleAccent,
                          onChanged: (bool value) {
                            debugPrint("Network switch value: $value"
                                "\Network state: ${_connectionStatus[index].name}");
                            setState(() {
                              // light = value;
                            });
                          },
                        ),
                      ],
                    )
                  ),
                ),
              ),
              const SizedBox(height: 40,),
            ],
          ),
        ),
      ),
    );
  }
}
