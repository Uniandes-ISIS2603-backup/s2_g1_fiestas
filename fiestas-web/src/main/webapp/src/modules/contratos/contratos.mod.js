
/**
 * @ngdoc overview
 * @name contratos.module:contratoModule
 * @description
 * Definición del módulo de Angular de Contratos. El módulo encapsula todos los 
 * controladores y los templates HTML que estén relacionados con los Contratos 
 * directamente. En la configuración del módulo se injecta la dependencia de 
 * ui.router que es la que se utiliza para la configuración de las URLs bajo las
 * cuales se accede al módulo. Por ejemplo, para mostrar los autores en la 
 * URL: 'localhost:8080/contratos/list' es necesario configurar el router por 
 * medio del stateProvider que informa a AngularJS de la relación entre la URL, 
 * un estado definido (estado de mostrar autores), el controlador y la vista 
 * correspondiente. Los estados definidos en este modulo son:
 * ```
 * | ESTADO           | URL                        | VISTAS                 |
 * |------------------|----------------------------|------------------------|
 * | contratos        | /contratos                 | mainView:              |
 * |                  |                            | contratos.html         |
 * |                  |                            |                        |
 * | contratosList    | /list                      | listView:              |
 * |                  |                            | contratos.list.html    |
 * |                  |                            |                        |
 * | contratoDetail   | /{contratoId:int}/detail   | listView:              |
 * |                  |                            | contratos.list.html    |
 * |                  |                            | detailView:            |
 * |                  |                            | contratos.detail.html  |
 * | contratosCreate  | /create                    | detailView: (/new)     |
 * |                  |                            | /contratos.new.html    |
 * | contratoUpdate   | /update/{contratoId:int}   | detailView: (/new)     |
 * |                  |                            | /contratos.new.html    |
 * | contratoDelete   | /delete/{contratoId:int}   | detailView: (/delete)  |
 * |                  |                            | /contrato.delete.html  |
 * |------------------|----------------------------|------------------------|
 *```
 */
(function (ng) {

    var mod = ng.module("contratoModule", ['ui.router']);
    mod.constant("contratosContext", "api/contratos");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/contratos/';
            //var basePathContratos = 'src/modules/contratos/';
            $urlRouterProvider.otherwise("/contratosList");

            $stateProvider.state('contratos', {
                url: '/contratos',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'contratos.html',
                        controller: 'contratoCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor','Cliente']
                }
            }).state('contratosList', {
                url: '/list',
                parent: 'contratos',
                views: {
                    'listView': {
                        templateUrl: basePath + 'contratos.list.html'
                    }
                }
            }).state('contratoDetail', {
                url: '/{contratoId:int}/detail',
                parent: 'contratos',
                param: {contratoId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'contratos.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'contratos.detail.html',
                        controller: 'contratoDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('contratosCreate', {
                url: '/create',
                parent: 'contratos',
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/contratos.new.html',
                        controller: 'contratoNewCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor','Cliente']
                }
            }).state('contratoUpdate', {
                url: '/update/{contratoId:int}',
                parent: 'contratos',
                param: {
                    contratoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/contratos.new.html',
                        controller: 'contratoUpdateCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor','Cliente']
                }
            }).state('contratoDelete', {
                url: '/delete/{contratoId:int}',
                parent: 'contratos',
                param: {
                    contratoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/delete/contratos.delete.html',
                        controller: 'contratoDeleteCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor','Cliente']
                }
            });
        }]);
})(window.angular);
