# DyrBok
DyrBok is an app for volunteers and employees working for an animal shelter. It enables sharing information and photos of the shelter animals.

## Introduction
The DyrBok app grew out of an idea to build a photo sharing app for Dierentehuis Stevenshage in Leiden (Netherlands). Besides photos, we have also added background information on the shelter animals and even included some small games. At the moment, we are testing the first version of the Android app. If you use an iPhone or an iPad: we are planning to build an iOS version of the app as well.

## Manuals
The use of the app is explained in the [user manual](/documentation/english/user-manual/user-manual.md). For application managers, there is an additional [application manager manual](/documentation/english/application-manager-manual/application-manager-manual.md). The internal test of the Android app is described in the [internal test manual](/documentation/english/internal-test-manual/internal-test-manual.md).

## Privacy policy
If you choose to share photos of shelter animals, these photos will be visible to other users and can be used by the animal shelter for additional purposes. For more details, see our [privacy policy](/documentation/english/privacy-policy.md) page.

## Delete my data
See the [delete my data](/documentation/english/delete-my-data.md) page for requesting deletion of some or all of your photos and/or deletion of your account.

## Acknowledgements
We want to thank all the people that have helped to create the DyrBok app: [acknowledgements](/documentation/english/acknowledgements.md).

## Technical background
This is a Kotlin Multiplatform project targeting Android, iOS and Desktop.

* `/composeApp` contains both code that is shared across the Multiplatform application and platform specific code.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - `androidMain` is for code that’s specific for Android.
  - `desktopMain` is for code that’s specific for Desktop.
  - `iosMain` is for code that’s specific for iOS.

* `/iosApp` contains the entry point for the iOS app.
