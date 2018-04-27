(function (ng) {
    var app = angular.module('mainApp', [
        // External dependencies
        'ui.router',
       
        // Internal modules dependencies       
        'citiesModule',
        'proveedorModule',
        'clienteModule',
        'blogsModule',
        'bonosModule',
        'servicioModule',
        'valoracionModule'
        'pagoModule',
        'eventoModule',
        'productoModule',
        'tematicasModule'


    ]);
    // Resuelve problemas de las promesas
    app.config(['$qProvider', function ($qProvider) {
            $qProvider.errorOnUnhandledRejections(false);
        }]);
})(window.angular);

