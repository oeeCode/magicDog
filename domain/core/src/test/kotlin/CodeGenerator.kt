import cn.hutool.setting.Setting
import com.baomidou.mybatisplus.generator.FastAutoGenerator
import com.baomidou.mybatisplus.generator.config.*
import com.baomidou.mybatisplus.generator.config.po.TableField
import com.baomidou.mybatisplus.generator.config.rules.DateType
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine
import com.baomidou.mybatisplus.generator.type.TypeRegistry
import com.makebk.common.base.ctrl.IBaseController
import com.makebk.common.base.mapper.IBaseMapper
import com.makebk.common.base.service.IBaseService
import com.makebk.common.base.service.IBaseServiceImpl
import java.sql.Types
import java.util.*

fun main() {

    val config = Setting("config.setting")
    FastAutoGenerator.create(
        config.getStr("url"),
        config.getStr("user"),
        config.getStr("password")
    ).globalConfig({ builder: GlobalConfig.Builder? ->
        builder!!.author("gme")
            .enableKotlin()
            .enableSwagger()
            .dateType(DateType.ONLY_DATE) // 设置时间类型策略
            .commentDate("yyyy-MM-dd") // 设置注释日期格式
            .outputDir("./domain/core/src/main/kotlin")
        })
        .dataSourceConfig({ builder: DataSourceConfig.Builder? ->
            builder!!.typeConvertHandler({ globalConfig: GlobalConfig?, typeRegistry: TypeRegistry?, metaInfo: TableField.MetaInfo? ->
                val typeCode = metaInfo!!.jdbcType.TYPE_CODE
                if (typeCode == Types.SMALLINT) {
                    DbColumnType.INTEGER
                }
                typeRegistry!!.getColumnType(metaInfo)
            })
        }
        )
        .packageConfig({ builder: PackageConfig.Builder? ->
            builder!!
                .parent("com.makebk.domain.core")
                .mapper("infrastructure.mapper")
                .entity("entits")
                .service("application.service")
                .controller("interfaces")
                .pathInfo(Collections.singletonMap(OutputFile.xml, "./domain/core/src/main/resources/mapper/core"))
        })
        .strategyConfig({ builder: StrategyConfig.Builder? ->
            builder!!
//                .addInclude("%")
                .addTablePrefix()

                .mapperBuilder()
                .superClass(IBaseMapper::class.java)
                .mapperTemplate("/templates/mapper.kt")
                .enableMapperAnnotation()
                .enableBaseResultMap()
                .enableBaseColumnList()

                .entityBuilder()
                .kotlinTemplatePath("/templates/entity.kt")

                .controllerBuilder()
                .template("/templates/controller.kt")
                .enableRestStyle()
                .superClass(IBaseController::class.java)

                .serviceBuilder()
                .superServiceClass(IBaseService::class.java)
                .superServiceImplClass(IBaseServiceImpl::class.java)
                .serviceTemplate("/templates/service.kt")

                .disableServiceImpl()
                .build()
        })
        .templateEngine(FreemarkerTemplateEngine())
        .execute()
}