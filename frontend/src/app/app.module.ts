import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AgmCoreModule } from '@agm/core';

import {AppComponent} from './components/app.component';
import {MapComponent} from './components/map/map.component';
import {ChartsComponent} from './components/charts/charts.component';
import {ChartComponent} from './components/chart/chart.component';


@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    ChartsComponent,
    ChartComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    AgmCoreModule.forRoot({
      apiKey: ''
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
