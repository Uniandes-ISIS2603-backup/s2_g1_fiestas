(function (ng) {
    var mod = ng.module("valoracionModule", ['servicioModule', 'ui.router']);
    mod.constant("valoracionesContext", "valoraciones");
    mod.constant("servicioContext", "api/servicio");

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/valoraciones/';
            $urlRouterProvider.otherwise("/valoracionesList");

            $stateProvider.state('valoraciones', {
                url: '/valoraciones',
                abstract: true,
                parent: 'servicioDetail',
                views: {
                    childrenView: {
                        templateUrl: basePath + 'valoraciones.html'
                    }
                }
                ,
                data: {
                    requireLogin: false,
                    roles: []
                }
            }).state('valoracionesList', {
                url: '/list',
                parent: 'valoraciones',
                views: {
                    'listView': {
                        templateUrl: basePath + 'valoraciones.list.html',
                        controller: 'valoracionesCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            });
        }]);
})(window.angular);