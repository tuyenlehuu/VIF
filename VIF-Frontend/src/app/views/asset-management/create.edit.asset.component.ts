import { Component, OnInit } from '@angular/core';
import { Asset } from '../../models/Asset.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AssetService } from '../../services/asset.service';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { GroupAsset } from '../../models/GroupAsset.model';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-other.asset',
  templateUrl: './create.edit.asset.component.html',
  styleUrls: ['./asset.component.scss']
})
export class CEAssetComponent implements OnInit {
  asset: Asset;
  sub: any;
  id: any;
  addAssetForm: FormGroup;
  submitted = false;
  editAssetForm: FormGroup;
  groupAssets: GroupAsset[] = [];

  typeAsset = [
    {
      name: 'Chứng khoán',
      value: 2
    },
    {
      name: 'Tài sản khác',
      value: 4
    }
  ];

  constructor(private route: ActivatedRoute, private assetService: AssetService, private router: Router,
    private toastrService: ToastrService, private fb: FormBuilder, private translateService: TranslateService) {
      this.assetService.getGroupAsset().pipe(first()).subscribe((res: any)=>{
        this.groupAssets = res.data;
      });
    }

  ngOnInit() {
    this.createForm();

    this.id = this.route.snapshot.params['id'];
    if (this.id > 0) {
      this.assetService.getAssetById(this.id).subscribe((res: Asset) => {
        this.asset = res;
        // console.log("current user: ", this.user);
        this.createEditForm();
      })
    } else {
      this.asset = new Asset();
    }
  }

  createForm() {
    this.addAssetForm = this.fb.group({
      groupAsset: [2, Validators.required],
      assetCode: ['', Validators.required],
      amount: [''],
      assetName: ['', Validators.required],
      currentPrice: ['', Validators.required],
      description: [''],
      orginalPrice: ['']
    });
  }

  createEditForm() {
    this.editAssetForm = this.fb.group({
    });
  }

  onAddSubmit() {

  }

  onChangeTypeAsset(){
    console.log("this.addAssetForm.value.typeAsset;", this.addAssetForm.value.typeAsset);
  }

}
