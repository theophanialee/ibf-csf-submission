import { inject } from "@angular/core";
import { ActivatedRoute, ActivatedRouteSnapshot, CanActivateFn, CanDeactivateFn, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { PictureComponent } from "./views/picture.component";


// export const enterWebcam: CanActivateFn =
//   (route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
//       : boolean | UrlTree | Promise<boolean | UrlTree> | Observable<boolean | UrlTree>  => {
//     return confirm('Are you sure you wish to see the polar bears?')
//   }

  export const leavePicComponent: CanDeactivateFn<PictureComponent> =
  (comp: PictureComponent, route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
      : boolean | UrlTree | Promise<boolean | UrlTree> | Observable<boolean | UrlTree>  => {

    const router = inject(Router)

    console.info('>>> form dirty: ', comp.isFormDirty())

    if (comp.isFormDirty())
      return confirm('Are you sure you want to discard image?')

    return router.parseUrl('/')
  }
