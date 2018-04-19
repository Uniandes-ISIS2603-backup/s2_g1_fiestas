(function (ng) {

    var mod = ng.module("eventoModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/eventos/';

            $urlRouterProvider.otherwise("/eventosList");

            $stateProvider.state('eventos', {
                url: '/eventos',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'eventos.html',
                        controller: 'eventoCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('eventosList', {
                url: '/list',
                parent: 'eventos',
                views: {
                    'listView': {
                        templateUrl: basePath + 'eventos.list.html'
                    }
                }
            }).state('eventoDetail', {
                url: '/{eventoId:int}/detail',
                parent: 'eventos',
                param: {eventoId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'eventos.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'eventos.detail.html',
                        controller: 'eventoDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('eventosCreate', {
                url: '/create',
                parent: 'eventos',
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/eventos.new.html',
                        controller: 'eventoNewCtrl'
                    }
                }
            });
        }
    ]);
})(window.angular);
