import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {AgmCoreModule} from '@agm/core';

import {AppComponent} from './components/app.component';
import {MapComponent} from './components/map/map.component';
import {ChartsComponent} from './components/charts/charts.component';
import {ChartComponent} from './components/chart/chart.component';
import {CommentsService} from './services/comments/comments.service';
import {ReadingsService} from './services/readings/readings.service';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';
import {ChartsModule} from 'ng2-charts';

const appRoutes: Routes = [
  {path: '', component: MapComponent},
  {path: 'charts', component: ChartsComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    ChartsComponent,
    ChartComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    CommonModule,
    FormsModule,
    HttpClientModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDQqOVuwHktKl2UMoIaFuT37k2qaGRQq6Y'
    }),
    ChartsModule
  ],
  providers: [CommentsService, ReadingsService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
