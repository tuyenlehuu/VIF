import { NgModule,ModuleWithProviders} from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ShareDevidendComponent } from './share-devidend.component';



const routes: Routes = [
  {
    path: '',
    component: ShareDevidendComponent,
    data: {
      title: 'Chia cổ tức'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShareDevidenRoutingModule {}
