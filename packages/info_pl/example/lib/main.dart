
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
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:info_pl/info_pl.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  static final InfoPl _infoPl = InfoPl();
  DeviceInfo? _deviceInfo;
  String? _deviceInfoText, _deviceBrandAndModel;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    String? deviceInfoText, deviceBrandAndModel;

    try {
      /// İnit Getting Device Info
      _deviceInfo = await _infoPl.init();
      /// Edited Device Info String
      deviceInfoText = _deviceInfo.toString();
      /// Access to Each Device Info Individually
      deviceBrandAndModel = '${_deviceInfo!.brand} ${_deviceInfo!.model}';
    } on PlatformException {
      deviceInfoText = 'Failed to get device info.';
      deviceBrandAndModel = 'Failed to get device brand and device model.';
    }

    if (!mounted) return;

    setState(() {
      _deviceInfoText = deviceInfoText!;
      _deviceBrandAndModel = deviceBrandAndModel;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        appBar: AppBar(
          title: Text(_deviceBrandAndModel ?? 'InfoPL'),
          centerTitle: true,
        ),
        body: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.symmetric(
                vertical: 15,
                horizontal: 10
            ),
            child: Text(
              _deviceInfoText ?? 'Loading...',
            ),
          ),
        ),
      ),
    );
  }
}
