import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { bootstrapApplication } from '@angular/platform-browser';
import { AppModule } from './app/app.module';
import { AppComponent } from './app/app.component';
import { initializeApp, provideFirebaseApp } from '@angular/fire/app';
import { getFirestore, provideFirestore } from '@angular/fire/firestore';
bootstrapApplication(AppComponent, {
  providers: [provideFirebaseApp(() => initializeApp({"projectId":"weblab1-d3d1d","appId":"1:891701820913:web:c59e92308b5f71e0cd5850","storageBucket":"weblab1-d3d1d.appspot.com","apiKey":"AIzaSyAxnlP5sYDznlPWQN1mfLpXoPeASA6xH9o","authDomain":"weblab1-d3d1d.firebaseapp.com","messagingSenderId":"891701820913"})), provideFirestore(() => getFirestore())]
});

platformBrowserDynamic()
  .bootstrapModule(AppModule)
  .catch((err) => console.error(err));
