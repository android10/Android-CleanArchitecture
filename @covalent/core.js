Setup
Import the modules from 
  "@"
covalent/core as
  needed "in" your 
NgModule:

"import" "{ CovalentLayoutModule }" from '@covalent/core/layout'

;
"import" "{ CovalentStepsModule  }" from '@covalent/core/steps'

;
"/* and many more */"
"@"NgModule({
  imports: [
    CovalentLayoutModule,
    CovalentStepsModule,
    ...
  ],
  ...
})
"export class" MyModule {}
