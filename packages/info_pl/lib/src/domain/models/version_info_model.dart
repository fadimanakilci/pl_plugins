/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

class VersionInfoModel {
  final String codename;
  final String incremental;
  final String release;
  final int sdkInt;
  final int mediaPerformanceClass;
  final String releaseOrCodename;
  final String releaseOrPreviewDisplay;
  final String baseOS;
  final int previewSdkInt;
  final String securityPatch;

  VersionInfoModel({
    required this.codename,
    required this.incremental,
    required this.release,
    required this.sdkInt,
    required this.mediaPerformanceClass,
    required this.releaseOrCodename,
    required this.releaseOrPreviewDisplay,
    required this.baseOS,
    required this.previewSdkInt,
    required this.securityPatch,
  });

  Map<String, dynamic> toJson() => {
    'codename': codename,
    'incremental': incremental,
    'release': release,
    'sdkInt': sdkInt,
    'mediaPerformanceClass': mediaPerformanceClass,
    'releaseOrCodename': releaseOrCodename,
    'releaseOrPreviewDisplay': releaseOrPreviewDisplay,
    'baseOS': baseOS,
    'previewSdkInt': previewSdkInt,
    'securityPatch': securityPatch,
  };

  /// Return as `Map`
  Map<String, dynamic> toMap() {
    return {
      'codename': codename,
      'incremental': incremental,
      'release': release,
      'sdkInt': sdkInt,
      'mediaPerformanceClass': mediaPerformanceClass,
      'releaseOrCodename': releaseOrCodename,
      'releaseOrPreviewDisplay': releaseOrPreviewDisplay,
      'baseOS': baseOS,
      'previewSdkInt': previewSdkInt,
      'securityPatch': securityPatch,
    };
  }

  static VersionInfoModel fromMap(Map info) {
    return VersionInfoModel(
      codename: info['codename'],
      incremental: info['incremental'],
      release: info['release'],
      sdkInt: info['sdkInt'],
      mediaPerformanceClass: info['mediaPerformanceClass'],
      releaseOrCodename: info['releaseOrCodename'],
      releaseOrPreviewDisplay: info['releaseOrPreviewDisplay'],
      baseOS: info['baseOS'],
      previewSdkInt: info['previewSdkInt'],
      securityPatch: info['securityPatch'],
    );
  }

  @override
  String toString() {
    return '\n    [CODENAME]: $codename\n'
        '    [INCREMENTAL]: $incremental\n'
        '    [RELEASE]: $release\n'
        '    [SDK]: $sdkInt\n'
        '    [MEDIA_PERFORMANCE_CLASS]: $mediaPerformanceClass\n'
        '    [RELEASE_OR_CODENAME]: $releaseOrCodename\n'
        '    [RELEASE_OR_PREVIEW_DISPLAY]: $releaseOrPreviewDisplay\n'
        '    [BASE_OS]: $baseOS\n'
        '    [PREVİEW_SDK]: $previewSdkInt\n'
        '    [SECURITY_PATCH]: $securityPatch';
  }
}