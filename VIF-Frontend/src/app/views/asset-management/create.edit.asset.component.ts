import { Component, OnInit } from '@angular/core';
import { Asset } from '../../models/Asset.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AssetService } from '../../services/asset.service';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { GroupAsset } from '../../models/GroupAsset.model';
import { first } from 'rxjs/operators';
import { Branch } from '../../models/Branch.model';
import { BranchService } from '../../services/branch.service';

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
  branchShares: Branch[] = [];
  branchCodeSelected: string;
  newAssetToAdd: Asset = new Asset();
  groupAssetSelectedId: number = 2;

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
    private toastrService: ToastrService, private fb: FormBuilder, private translateService: TranslateService,
    private branchService: BranchService) {
    // this.assetService.getGroupAsset().pipe(first()).subscribe((res: any)=>{
    //   this.groupAssets = res.data;
    // });
    this.branchService.getAll().pipe(first()).subscribe((res: any) => {
      this.branchShares = res;
      if (this.branchShares.length > 0) {
        this.branchCodeSelected = this.branchShares[0].branchCode;
        this.createForm();
      }
    });
  }

  ngOnInit() {


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
      amount: [0],
      assetName: ['', Validators.required],
      currentPrice: [0, Validators.required],
      description: [''],
      orginalPrice: [0],
      branchCode: [this.branchCodeSelected != null ? this.branchCodeSelected : '']
    });
  }

  createEditForm() {
    this.editAssetForm = this.fb.group({
    });
  }

  onAddSubmit() {
    if (this.addAssetForm.invalid) {
      return;
    }

    this.newAssetToAdd.activeFlg = 1;
    this.newAssetToAdd.amount = this.addAssetForm.value.amount!=null?this.addAssetForm.value.amount:0;
    this.newAssetToAdd.assetCode = this.addAssetForm.value.assetCode;
    this.newAssetToAdd.assetName = this.addAssetForm.value.assetName;
    this.newAssetToAdd.currentPrice = this.addAssetForm.value.currentPrice!=null?this.addAssetForm.value.currentPrice:0;
    this.newAssetToAdd.description = this.addAssetForm.value.description;
    this.newAssetToAdd.orginalPrice = this.addAssetForm.value.orginalPrice!=null?this.addAssetForm.value.orginalPrice:0;
    
    if (this.addAssetForm.value.groupAsset == 4) {
      this.newAssetToAdd.branchCode = null;
    }else{
      this.newAssetToAdd.branchCode = this.addAssetForm.value.branchCode;
    }

    console.log("this.newAssetToAdd", this.newAssetToAdd);
  }

  get addForm() { return this.addAssetForm.controls; }

  get editForm() { return this.editAssetForm.controls; }


  onChangeTypeAsset() {
    // console.log("this.addAssetForm.value.typeAsset;", this.addAssetForm.value.typeAsset);
    this.groupAssetSelectedId = this.addAssetForm.value.groupAsset;
    if (this.groupAssetSelectedId == 4) {
      this.newAssetToAdd.branchCode = null;
    }else{
      this.newAssetToAdd.branchCode = this.branchCodeSelected;
    }
    let mGroup: GroupAsset = new GroupAsset();
    mGroup.id = this.groupAssetSelectedId;
    mGroup.type_of_asset = this.groupAssetSelectedId;
    this.newAssetToAdd.groupAsset = mGroup;
  }

}
